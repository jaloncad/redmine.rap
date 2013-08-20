
// Package
package org.centerom.almistack.ui.editorinputs;

// Imports
import org.centerom.almistack.domain.beans.Product;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;


public class ProductEditorInput implements IEditorInput {

	
	// TODO: remove when configuration and internationalization is available
	// |
	// v
	private static final String TOOL_TIP = "Editor for ";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	private Product product = null;


	public ProductEditorInput(Product product) {
		this.product = product;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return product;
	}

	@Override
	public boolean exists() {
		return Boolean.TRUE;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return product.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return TOOL_TIP + this.getName();
	}

    @Override
    public boolean equals(Object obj) {
    	// Locals
    	Boolean result = Boolean.TRUE;

        if (this == obj) {
        	result = Boolean.TRUE;
        }
        else if (  obj == null
        		|| getClass() != obj.getClass()) {
        	result = Boolean.FALSE;
        }
        else {
        	// Match identifiers: it is enough
        	if (product.getId()
        			   .toString()
        			   .equalsIgnoreCase(
        					   ((ProductEditorInput) obj).product.getId()
        					   							 .toString())) {
        		result = Boolean.TRUE;
        	}
        	else {
        		result = Boolean.FALSE;
        	}
        }
        return result;
    }

    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
// END <ProductEditorInput> class
// --- -------------------- -----