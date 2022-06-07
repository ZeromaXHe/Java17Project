## Multiplayer

Since 11.11, FXGL supports object replication, which helps with synchronizing entities, input, properties, etc. across multiple machines.

TODO:

## Networking

Present in FXGL 11.11+. [YouTube tutorial](https://www.youtube.com/watch?v=-sXkHTQKszk)

The `fxgl-net` module contains the `NetService` API, which can be obtained by calling `FXGL.getNetService()`. The service provides developers with the ability to send arbitrary data from one endpoint (e.g. server) to another (e.g. client). Both single and multiple clients are supported. The FXGL networking API provides the same high-level API to both TCP and UDP communications.

Minimal example TCP connection (server):

```
var server = FXGL.getNetService().newTCPServer(55555);
server.setOnConnected(connection -> {
    connection.addMessageHandlerFX((conn, message) -> {
        // do something with message when received from client
        // FX means this callback runs on JavaFX thread
    });
});

server.startAsync();
```

(TCP client to connect to above server)

```
var client = FXGL.getNetService().newTCPClient("localhost", 55555);
client.setOnConnected(connection -> {
    connection.addMessageHandlerFX((conn, message) -> {
        // do something with message when received from server
        // FX means this callback runs on JavaFX thread
    });
});

client.connectAsync();
```

#### Sending data

You can send data either using a single `Connection` object (to that connection), or using the `Server` object (to all active connections).

Single connection:

```
var connnection = ...
var data = new Bundle("");
data.put("key", "value");

connection.send(data);
```

All active connections:

```
var server = ...
var data = new Bundle("");
data.put("key", "value");

server.broadcast(data);
```

Full sample:

```

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Server;
import javafx.scene.control.CheckBox;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class NetworkingSample extends GameApplication {

    private boolean isServer;

    private Server<Bundle> server;

    @Override
    protected void initSettings(GameSettings settings) { }

    @Override
    protected void initGame() {
        var cb = new CheckBox();
        cb.selectedProperty().addListener((o, old, isSelected) -> {
            var bundle = new Bundle("CheckBoxData");
            bundle.put("isSelected", isSelected);

            if (isServer)
                server.broadcast(bundle);
        });

        addUINode(cb, 100, 100);

        runOnce(() -> {
            getDialogService().showConfirmationBox("Is Server?", answer -> {
                isServer = answer;

                if (isServer) {
                    server = getNetService().newTCPServer(55555);
                    server.startAsync();
                } else {
                    var client = getNetService().newTCPClient("localhost", 55555);
                    client.setOnConnected(connection -> {
                        connection.addMessageHandlerFX((conn, message) -> {
                            boolean isSelected = message.get("isSelected");

                            cb.setSelected(isSelected);
                        });
                    });
                    client.connectAsync();
                }
            });
        }, Duration.seconds(0.2));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

#### Custom Readers / Writers

Note: when using custom readers / writers, you are responsible for gracefully closing any custom read/write threads. High-level pseudo-code for how FXGL will handle connections. 

```

while (connection.isConnected()) {
    try {
        var message = reader.read();
    } catch (Exception e) {}
}

connections.remove(connection);
```