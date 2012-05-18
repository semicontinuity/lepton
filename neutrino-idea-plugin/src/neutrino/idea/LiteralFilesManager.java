package neutrino.idea;

import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class LiteralFilesManager extends AbstractProjectComponent {
    public static LiteralFilesManager getInstance(Project project) {
        return project.getComponent(LiteralFilesManager.class);
    }

    public LiteralFilesManager(Project project) {
        super(project);
    }

    @NotNull
    public String getComponentName() {
        return "LiteralFilesManager";
    }
}