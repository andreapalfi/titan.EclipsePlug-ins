/******************************************************************************
 * Copyright (c) 2000-2017 Ericsson Telecom AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.titanium.refactoring.lazyfication;

import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.titan.common.logging.ErrorReporter;
import org.eclipse.titan.designer.Activator;
import org.eclipse.titan.designer.editors.ttcn3editor.TTCN3Editor;
import org.eclipse.titanium.refactoring.Utils;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class handles the {@link LazyficationRefactoring} class when the operation is
 * called from the package browser for a single or multiple project(s), folder(s) or file(s).
 * <p>
 * {@link #execute(ExecutionEvent)} is called by the UI (see plugin.xml).
 *
 * @author Istvan Bohm
 */
public class LazyficationActionFromBrowser extends AbstractHandler implements IObjectActionDelegate {
	private ISelection selection;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		performLazyfication();
		return null;
	}
	@Override
	public void run(final IAction action) {
		performLazyfication();
	}
	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		this.selection = selection;
	}
	@Override
	public void setActivePart(final IAction action, final IWorkbenchPart targetPart) {
	}

	private void performLazyfication() {
		// getting the active editor
		final TTCN3Editor targetEditor = Utils.getActiveEditor();
		//find selection
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		final IStructuredSelection structSelection = (IStructuredSelection)selection;
		final Set<IProject> projsToUpdate = Utils.findAllProjectsInSelection(structSelection);

		//update AST before refactoring
		Utils.updateASTBeforeRefactoring(projsToUpdate, "Lazyfication");
		Activator.getDefault().pauseHandlingResourceChanges();

		//create refactoring
		final LazyficationRefactoring refactoring = new LazyficationRefactoring(structSelection);
		//open wizard
		final LazyficationWizard wiz = new LazyficationWizard(refactoring);
		final RefactoringWizardOpenOperation operation = new RefactoringWizardOpenOperation(wiz);
		try {
			operation.run(targetEditor == null ? null : targetEditor.getEditorSite().getShell(), "");
		} catch (InterruptedException irex) {
			// operation was cancelled
		} catch (Exception e) {
			ErrorReporter.logError("LazyficationActionFromBrowser: Error while performing refactoring change! ");
			ErrorReporter.logExceptionStackTrace(e);
		}
		Activator.getDefault().resumeHandlingResourceChanges();
		//update AST after refactoring
		Utils.updateASTAfterRefactoring(wiz, refactoring.getAffectedObjects(), refactoring.getName());
	}

}