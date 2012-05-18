package neutrino.idea.editor.impl;


import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.fileEditor.ex.FileEditorProviderManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class LiteralFileEditorImpl extends UserDataHolderBase implements FileEditor {
    private FileEditorProvider provider;

    public LiteralFileEditorImpl(@NotNull Project project, @NotNull VirtualFile file) {
        this.provider = FileEditorProviderManager.getInstance().getProvider("text-editor");
        System.out.println("provider = " + provider);
    }

    @NotNull
    public JComponent getComponent() {
        return new JButton("hi");
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return "LiteralFileEditor";
    }

    @NotNull
    @Override
    public FileEditorState getState(@NotNull FileEditorStateLevel fileEditorStateLevel) {
        return new FileEditorState() {
            @Override
            public boolean canBeMergedWith(FileEditorState fileEditorState, FileEditorStateLevel fileEditorStateLevel) {
                return false;
            }
        };
    }

    @Override
    public void setState(@NotNull FileEditorState fileEditorState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isModified() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isValid() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void selectNotify() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deselectNotify() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StructureViewBuilder getStructureViewBuilder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
