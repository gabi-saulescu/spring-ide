/*******************************************************************************
 * Copyright (c) 2005, 2007 Spring IDE Developers
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Spring IDE Developers - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.beans.ui.editor.namespaces.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.springframework.ide.eclipse.beans.ui.editor.hyperlink.AbstractHyperlinkDetector;
import org.springframework.ide.eclipse.beans.ui.editor.hyperlink.JavaElementHyperlink;
import org.springframework.ide.eclipse.beans.ui.editor.util.BeansEditorUtils;
import org.springframework.ide.eclipse.core.java.JdtUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

/**
 * Detects hyperlinks in XML tags. Includes detection of bean classes and bean
 * properties in attribute values. Resolves bean references (including
 * references to parent beans or factory beans).
 * 
 * @author Christian Dupuis
 */
public class UtilHyperlinkDetector extends AbstractHyperlinkDetector implements
		IHyperlinkDetector {

	/**
	 * Returns <code>true</code> if given attribute is openable.
	 */
	@Override
	protected boolean isLinkableAttr(Attr attr) {
		String attrName = attr.getName();
		return ("list-class".equals(attrName) || "map-class".equals(attrName)
				|| "set-class".equals(attrName)
				|| "value-type".equals(attrName) || "key-type".equals(attrName));

	}

	@Override
	protected IHyperlink createHyperlink(String name, String target,
			Node parentNode, IRegion hyperlinkRegion, IDocument document,
			Node node, ITextViewer textViewer, IRegion cursor) {
		if (name == null) {
			return null;
		}
		IFile file = BeansEditorUtils.getFile(document);
		IType type = JdtUtils.getJavaType(file.getProject(), target);
		if (type != null) {
			return new JavaElementHyperlink(hyperlinkRegion, type);
		}
		return null;
	}
}