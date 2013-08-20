
// 
package org.centerom.almistack.ui.rap.pages;

// 
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.widgets.Composite;


public class Login extends AbstractEntryPoint {

	
	
	
	
	@Override
	protected void createContents(Composite parent) {
		
		

		try {

			// Holder holder = new Holder(parent);
			
			
			
			/*
			toolkit = new FormToolkit(parent.getDisplay());

			main = toolkit.createComposite(parent);
			main.setLayout(new GridLayout(1, Boolean.TRUE));
			main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			
			
			/*
			Section section = toolkit.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR | ExpandableComposite.EXPANDED);

			section.setText("Details for selected task");
			section.setLayout(new GridLayout(1, false));
			*/
						

			/*
			
			Composite header   = toolkit.createComposite(main);
			
			header.set
			
			Composite workarea = toolkit.createComposite(main);
			Composite footer   = toolkit.createComposite(main);
*/

			
			/*
			leftComposite.setLayout(new GridLayout(2, Boolean.FALSE));
			rightComposite.setLayout(new GridLayout(2, Boolean.FALSE));
			rightComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			// First composite
			toolkit.createLabel(leftComposite, "Id");
			txtId = toolkit.createText(leftComposite, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
			
			toolkit.createLabel(leftComposite, "Priority");
			txtPriority = toolkit.createText(leftComposite, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
			
			toolkit.createLabel(leftComposite, "Status");
			txtStatus = toolkit.createText(leftComposite, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
			
			toolkit.createLabel(leftComposite, "Estimated hours");
			txtEstimatedHours = toolkit.createText(leftComposite, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);

			toolkit.createLabel(leftComposite, "Charged hours");
			txtChargedHours = toolkit.createText(leftComposite, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
			
			// Second composite
			toolkit.createLabel(rightComposite, "Subject");
			txtSubject = toolkit.createText(rightComposite, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
			
			toolkit.createLabel(rightComposite, "Description");
			txtDescription = toolkit.createText(rightComposite, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
			txtDescription.setLayoutData(new GridData(GridData.FILL_BOTH));
			
			toolkit.createLabel(rightComposite, "Assignee");
			txtAssignee = toolkit.createText(rightComposite, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
			txtAssignee.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			*/
			
		} catch (Exception e) {

			e.printStackTrace();
			
			System.out.println("");
		}
	    // IPerspectiveRegistry registry = workbench.getPerspectiveRegistry();
	    // final IPerspectiveDescriptor[] perspectives = registry.getPerspectives();
	   //IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
	   // final IWorkbenchPage page = window.getActivePage();
	    //page.setPerspective( perspectives[ 0 ] );
	}

}



// ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:config.xml");