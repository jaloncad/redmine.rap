redmine.rap
===========

PoC from Center for Open Middleware (http://www.centeropenmiddleware.com/ "Center for Open Middleware") to provide Redmine with agile UI based on Eclipse RAP. Applying Single-sourcing
to have both web and desktop clients.

The PoC needs to be completed with more functionality but the purpose by now is to check the viability of the solution. The implementation at the moment has read-only data, 
a perspective and Editors only for Releases.

We'd like to have this solution to configure and agile project on Redmine. The TODO list would be:

* Use drag and drop to configure Releases in Iterations.
* Use drag and drop to configure User Stories for Releases.
* Generate War file for RAP client from a new .warproduct definition (Build with Eclipse Tycho)
* TODO


Prerequisites
-------------

This PoC is tested with Eclipse for RCP and RAP Developers (Version: Kepler Release, Build id: 20130207-1142)
but may also run with previous versions like Eclipse Juno.

An installation of Redmine with REST api plugin would be necessary to run the application.

The best option for single-sourcing is to develop using two different workspaces (RAP and RCP) with the commons plugins and the fragments
needed only for the working workspace. Also is preferable to develop with RAP and next go to Destkop, because RAP is a subset of the
the RCP capabilities.

Single-sourcing common plugins:

* org.centerom.almistack.core
* org.centerom.almistack.dependencies
* org.centerom.almistack.domain
* org.centerom.almistack.services
* org.centerom.almistack.servicesimpl
* org.centerom.almistack.ui

And the fragments (used for developing concrete targeted source):

* org.centerom.almistack.ui.rcp
* org.centerom.almistack.ui.rap


Configuration
-------------

The Launch configurations and target platforms for rcp and rap are located in:

* org.centerom.almistack.targetplatform.rcp
* org.centerom.almistack.targetplatform.rap

The configuration for Redmine host is located in 
> org.centerom.almistack.servicesimpl/src/main/resources/config.connector.redmine.redmineConnectorService.properties

# Connection parameters to change
connection.host   = http://180.106.44.7/redmine/
connection.apiKey = 7a7318f9796aa16c00078dea2a1f6b6f7427a157

The logservice is also configurable in 
> org.centerom.almistack.servicesimpl/src/main/resources/config.logger.loggerService.properties

Execution
---------
In each workspace you need to have only the fragments and tragetplatform for the target environment RAP or RCP.

It's easy to launch with the *.launch provided in the targetplatform plugins one for each environment.

RCP result:

![RCP screenshot](https://raw.github.com/jaloncad/redmine.rap/master/rcp.jpg)

RAP result:

![RAP screenshot](https://raw.github.com/jaloncad/redmine.rap/master/rap.jpg)
