# custom-magnolia-superuser-resetter
A custom module to brutally reset magnolia superuser credentials on startup. Intended to recover local content easily. Obviously shouldn't ever be deployed on publicly accessible nodes.

The superuser account will have its password reset to the default, and the account will be reenabled if it wasn't enabled before.

Just drop it on Magnolias classpath, and start the node. Supports Magnolia 5.5.6 and newer.

Reset will be performed on each startup while the module remains on the classpath.
