package se.geardo.magnolia.module.superuserresetter.setup;

import info.magnolia.cms.security.SecurityUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.AbstractRepositoryTask;
import info.magnolia.repository.RepositoryConstants;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResetSuperuserTask extends AbstractRepositoryTask {

  private static final Logger LOG = LoggerFactory.getLogger(ResetSuperuserTask.class);

  private static final String JCR_NODE_SUPERUSER = "/system/superuser";
  private static final String JCR_PROPERTY_ENABLED = "enabled";
  private static final String JCR_PROPERTY_PSWD = "pswd";

  public ResetSuperuserTask(String name, String description) {
    super(name, description);
  }

  @Override
  protected void doExecute(InstallContext installContext) throws RepositoryException {

    Session session = installContext.getJCRSession(RepositoryConstants.USERS);
    Node superuserNode = SessionUtil.getNode(session, JCR_NODE_SUPERUSER);

    boolean currentEnabled = PropertyUtil.getBoolean(superuserNode, JCR_PROPERTY_ENABLED, false);
    String currentPswd = PropertyUtil.getString(superuserNode, JCR_PROPERTY_PSWD, "");

    boolean dirty = false;

    if (!currentEnabled) {
      PropertyUtil.setProperty(superuserNode, JCR_PROPERTY_ENABLED, true);
      dirty = true;
    }

    if (!SecurityUtil.matchBCrypted("superuser", currentPswd)) {
      PropertyUtil.setProperty(
          superuserNode, JCR_PROPERTY_PSWD, SecurityUtil.getBCrypt("superuser"));
      dirty = true;
    }

    if (dirty) {
      session.save();
      LOG.info("Superuser account was reset to the defaults.");
    } else {
      LOG.debug("Superuser account information not altered.");
    }
  }
}
