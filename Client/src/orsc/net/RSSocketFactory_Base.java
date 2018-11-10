package orsc.net;

import orsc.util.GenUtil;

import java.io.IOException;
import java.net.Socket;

public abstract class RSSocketFactory_Base {
	String socketHost;
	int socketPort;

	final Socket openRaw() throws IOException {
		try {
			return new Socket(this.socketHost, this.socketPort);
		} catch (RuntimeException var3) {
			throw GenUtil.makeThrowable(var3, "m.C(" + false + ')');
		}
	}

	public abstract Socket open() throws IOException;

	public static final RSSocketFactory_Base createRSSocketConnection(String host, int port) {
		try {
			RSSocketFactory var3 = new RSSocketFactory();
			var3.socketHost = host;
			var3.socketPort = port;
			return var3;
		} catch (RuntimeException var4) {
			throw GenUtil.makeThrowable(var4,
					"na.B(" + "dummy" + ',' + port + ',' + (host != null ? "{...}" : "null") + ')');
		}
	}
}
