/**
 * Database migration to register the SeaTable plugin in Appsmith.
 *
 * This ChangeSet must be added to DatabaseChangelog2.java in the Appsmith server module:
 * app/server/appsmith-server/src/main/java/com/appsmith/server/migrations/DatabaseChangelog2.java
 */

@ChangeSet(order = "044", id = "add-seatable-plugin", author = "")
public void addSeaTablePlugin(MongoTemplate mongoTemplate) {
    Plugin plugin = new Plugin();
    plugin.setName("SeaTable");
    plugin.setType(PluginType.SAAS);
    plugin.setPackageName("seatable-plugin");
    plugin.setUiComponent("UQIDbEditorForm");
    plugin.setResponseType(Plugin.ResponseType.JSON);
    plugin.setIconLocation("https://seatable.com/favicon.svg");
    plugin.setDocumentationLink("https://api.seatable.com/");
    plugin.setDefaultInstall(true);
    try {
        mongoTemplate.insert(plugin);
    } catch (DuplicateKeyException e) {
        log.warn(plugin.getPackageName() + " already present in database.");
    }
    installPluginToAllWorkspaces(mongoTemplate, plugin.getId());
}
