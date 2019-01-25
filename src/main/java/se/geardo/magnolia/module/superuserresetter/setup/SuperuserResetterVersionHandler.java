package se.geardo.magnolia.module.superuserresetter.setup;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.Task;
import java.util.ArrayList;
import java.util.List;

public class SuperuserResetterVersionHandler extends DefaultModuleVersionHandler {

  @Override
  protected List<Task> getStartupTasks(InstallContext installContext) {
    List<Task> tasks = new ArrayList<>(super.getStartupTasks(installContext));
    tasks.add(new ResetSuperuserTask("Superuser reset", "Reset superuser account on startup"));
    return tasks;
  }
}
