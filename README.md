# SeaTable Connector for Appsmith

A native [Appsmith](https://www.appsmith.com/) data source plugin for [SeaTable](https://seatable.com/) — the open-source database platform that's easy like a spreadsheet and powerful like a database.

## Features

- **List Rows** — Query rows with filtering, sorting, pagination
- **Get Row** — Fetch a single row by ID
- **Create Row** — Insert new rows
- **Update Row** — Modify existing rows
- **Delete Row** — Remove rows
- **List Tables** — Retrieve base metadata (tables, columns, types)
- **SQL Query** — Execute SeaTable SQL queries

## Authentication

The connector uses SeaTable's **API Token** authentication:

1. In SeaTable, go to your base → **API Token** → create a new token
2. In Appsmith, create a new SeaTable datasource
3. Enter your **Server URL** (e.g., `https://cloud.seatable.io`) and **API Token**
4. The connector automatically exchanges the API token for a base access token

## Installation

### For Appsmith PR

Copy the `seaTablePlugin/` directory into the Appsmith source:

```bash
cp -r seaTablePlugin/ /path/to/appsmith/app/server/appsmith-plugins/seaTablePlugin/
```

Then add the module to `app/server/appsmith-plugins/pom.xml`:

```xml
<module>seaTablePlugin</module>
```

And add the database migration from `migrations/add-seatable-plugin.java` to `DatabaseChangelog2.java`.

## Plugin Structure

```
seaTablePlugin/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/external/
│   │   │   ├── plugins/
│   │   │   │   ├── SeaTablePlugin.java          # Main plugin class
│   │   │   │   └── exceptions/
│   │   │   │       ├── SeaTablePluginError.java
│   │   │   │       └── SeaTableErrorMessages.java
│   │   │   └── constants/
│   │   │       └── FieldName.java
│   │   └── resources/
│   │       ├── form.json                         # Datasource config form
│   │       ├── setting.json                      # Query settings
│   │       ├── plugin.properties
│   │       └── editor/
│   │           ├── root.json                     # Command selector
│   │           ├── listRows.json
│   │           ├── getRow.json
│   │           ├── createRow.json
│   │           ├── updateRow.json
│   │           ├── deleteRow.json
│   │           ├── listTables.json
│   │           └── sqlQuery.json
│   └── test/
│       └── java/com/external/plugins/
│           └── SeaTablePluginTest.java           # Unit tests (MockWebServer)
```

## SeaTable API Flow

```
1. User configures: Server URL + API Token (base-level)
2. Plugin calls:  GET {server}/api/v2.1/dtable/app-access-token/
                  → returns: access_token, dtable_uuid, dtable_server
3. All queries:   {dtable_server}api/v2/dtables/{dtable_uuid}/rows/
                  with header: Authorization: Token {access_token}
```

## API Endpoints Used

| Command | Method | Endpoint |
|---|---|---|
| List Rows | GET | `/api/v2/dtables/{uuid}/rows/` |
| Get Row | GET | `/api/v2/dtables/{uuid}/rows/{row_id}/` |
| Create Row | POST | `/api/v2/dtables/{uuid}/rows/` |
| Update Row | PUT | `/api/v2/dtables/{uuid}/rows/` |
| Delete Row | DELETE | `/api/v2/dtables/{uuid}/rows/` |
| List Tables | GET | `/api/v2/dtables/{uuid}/metadata/` |
| SQL Query | POST | `/api/v2/dtables/{uuid}/sql/` |

All endpoints are verified against the [SeaTable OpenAPI specification](https://api.seatable.com/).

## Related

- [SeaTable API Reference](https://api.seatable.com/)
- [SeaTable Developer Documentation](https://developer.seatable.com/)
- [Appsmith Plugin Contribution Guidelines](https://github.com/appsmithorg/appsmith/blob/release/contributions/ServerCodeContributionsGuidelines/PluginCodeContributionsGuidelines.md)
- [SeaTable ToolJet Connector](https://github.com/seatable/tooljet-connector)

## License

Apache License 2.0 (consistent with Appsmith)
