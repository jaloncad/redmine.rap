package org.centerom.almistack.servicesimpl.connector.redmine.internal;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.Attachment;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.Issue;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.IssueCategory;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.IssuePriority;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.IssueRelation;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.IssueStatus;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.Membership;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.News;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.Project;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.Role;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.SavedQuery;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.TimeEntry;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.TimeEntryActivity;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.Tracker;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.User;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.Version;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions.RedmineInternalError;

public class URIConfigurator {
	private static final String URL_POSTFIX = ".json";

	private static final Map<Class<?>, String> urls = new HashMap<Class<?>, String>();

	static {
		urls.put(User.class, "users");
		urls.put(Group.class, "groups");
		urls.put(Issue.class, "issues");
		urls.put(Project.class, "projects");
		urls.put(TimeEntry.class, "time_entries");
		urls.put(SavedQuery.class, "queries");
		urls.put(IssueStatus.class, "issue_statuses");
		urls.put(Version.class, "versions");
		urls.put(IssueCategory.class, "issue_categories");
		urls.put(Tracker.class, "trackers");
		urls.put(Attachment.class, "attachments");
		urls.put(News.class, "news");
		urls.put(IssueRelation.class, "relations");
		urls.put(Role.class, "roles");
		urls.put(Membership.class, "memberships");
		urls.put(IssuePriority.class, "enumerations/issue_priorities");
        urls.put(TimeEntryActivity.class, "enumerations/time_entry_activities");
	}

	private final URL baseURL;
	private final String apiAccessKey;

	public URIConfigurator(String host, String apiAccessKey) {
		if (host == null || host.isEmpty()) {
			throw new IllegalArgumentException(
					"The host parameter is NULL or empty");
		}
		try {
			this.baseURL = new URL(host);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Illegal host URL " + host, e);
		}
		this.apiAccessKey = apiAccessKey;
	}

	public URI createURI(String query) {
		return createURI(query, new ArrayList<NameValuePair>());
	}

	public URI createURI(String query, NameValuePair... param) {
		return createURI(query, Arrays.asList(param));
	}

	/**
	 * @param query
	 *            e.g. "/issues.xml"
	 * @return URI with auth parameter "key" if not in "basic auth mode.
	 */
	private URI createURI(String query,
			Collection<? extends NameValuePair> origParams) {
		final List<NameValuePair> params = new ArrayList<NameValuePair>(
				origParams);
		if (apiAccessKey != null) {
			params.add(new BasicNameValuePair("key", apiAccessKey));
		}
		URI uri;
		try {
			URL url = baseURL;
			String path = url.getPath();
			if (!query.isEmpty()) {
				path += "/" + query;
			}
			uri = URIUtils.createURI(url.getProtocol(), url.getHost(),
					url.getPort(), path,
					URLEncodedUtils.format(params, "UTF-8"), null);
		} catch (URISyntaxException e) {
			throw new RedmineInternalError(e);
		}
		return uri;
	}

	public URI getChildObjectsURI(Class<?> parent, String parentId,
			Class<?> child, NameValuePair... args) {
		final String base = getConfig(parent);
		final String detal = getConfig(child);
		return createURI(base + "/" + parentId + "/" + detal + URL_POSTFIX,
				args);
	}

	public URI getObjectsURI(Class<?> child, NameValuePair... args) {
		final String detal = getConfig(child);
		return createURI(detal + URL_POSTFIX, args);
	}

	public URI getObjectsURI(Class<?> child,
			Collection<? extends NameValuePair> args) {
		final String detal = getConfig(child);
		return createURI(detal + URL_POSTFIX, args);
	}

	public URI getObjectURI(Class<?> object, String id, NameValuePair... args) {
		final String detal = getConfig(object);
		return createURI(detal + "/" + id + URL_POSTFIX, args);
	}

	private String getConfig(Class<?> item) {
		final String guess = urls.get(item);
		if (guess == null)
			throw new RedmineInternalError("Unsupported item class "
					+ item.getCanonicalName());
		return guess;
	}

	public URI getUploadURI() {
		return createURI("uploads" + URL_POSTFIX);
	}
}
