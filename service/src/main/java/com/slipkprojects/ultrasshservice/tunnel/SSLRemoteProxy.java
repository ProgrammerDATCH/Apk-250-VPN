package com.slipkprojects.ultrasshservice.tunnel;

import android.content.SharedPreferences;
import com.trilead.ssh2.ProxyData;
import com.trilead.ssh2.crypto.Base64;
import com.trilead.ssh2.transport.ClientServerHello;
import com.slipkprojects.ultrasshservice.logger.SkStatus;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.HandshakeCompletedEvent;
import java.util.Arrays;
import com.slipkprojects.ultrasshservice.SocksHttpService;

public class SSLRemoteProxy implements ProxyData {

	class HandshakeTunnelCompletedListener implements HandshakeCompletedListener {
        private final String val$host;
        private final int val$port;
        private final SSLSocket val$sslSocket;

        HandshakeTunnelCompletedListener( String str, int i, SSLSocket sSLSocket) {
            this.val$host = str;
            this.val$port = i;
            this.val$sslSocket = sSLSocket;
        }

		public void handshakeCompleted(HandshakeCompletedEvent handshakeCompletedEvent) {
			// addLog(new StringBuffer().append("<b>Established ").append(handshakeCompletedEvent.getSession().getProtocol()).append(" connection with ").append(val$host).append(":").append(this.val$port).append(" using ").append(handshakeCompletedEvent.getCipherSuite()).append("</b>").toString());
			//addLog(new StringBuffer().append("<b>Established ").append(handshakeCompletedEvent.getSession().getProtocol()).append(" connection ").append("using ").append(handshakeCompletedEvent.getCipherSuite()).append("</b>").toString());
		    //addLog(new StringBuffer().append("Supported cipher suites: ").append(Arrays.toString(this.val$sslSocket.getSupportedCipherSuites())).toString());
            //addLog(new StringBuffer().append("Enabled cipher suites: ").append(Arrays.toString(this.val$sslSocket.getEnabledCipherSuites())).toString());
            //SkStatus.logInfo(new StringBuffer().append("SSL: Supported protocols: <br>").append(Arrays.toString(val$sslSocket.getSupportedProtocols())).toString().replace("[", "").replace("]", "").replace(",", "<br>"));
			//SkStatus.logInfo(new StringBuffer().append("SSL: Enabled protocols: <br>").append(Arrays.toString(val$sslSocket.getEnabledProtocols())).toString().replace("[", "").replace("]", "").replace(",", "<br>"));
			//SkStatus.logInfo("SSL: Using cipher " + handshakeCompletedEvent.getSession().getCipherSuite());
			SkStatus.logInfo("SSL: Using protocol " + handshakeCompletedEvent.getSession().getProtocol());
			SkStatus.logInfo("SSL: Handshake finished");
        }
    }




    private Socket mSocket;
    private String proxyPass;
    private String proxyUser;
    private String requestPayload;
    private SharedPreferences sp;
    private String stunnelHostSNI;
    private int stunnelPort = 443;
    private String stunnelServer;

    @Override
    public Socket openConnection(String str, int i, int i2, int i3) throws IOException {
        this.mSocket = SocketChannel.open().socket();
        this.mSocket.connect(new InetSocketAddress(this.stunnelServer, this.stunnelPort));
        if (!this.mSocket.isConnected()) {
            return this.mSocket;


        }
		mSocket.setKeepAlive(true);
		mSocket.setTcpNoDelay(true);
		//SkStatus.logInfo("SSL KEEP ALIVE: " + mSocket.getKeepAlive());
		//SkStatus.logInfo("SSL TCP DELAY: " + mSocket.getTcpNoDelay());
        this.mSocket = doSSLHandshake(str, this.stunnelHostSNI, i);
        String requestPayload = getRequestPayload(str, i);
        OutputStream outputStream = this.mSocket.getOutputStream();
        if (!TunnelUtils.injectSplitPayload(requestPayload, outputStream)) {
            try {
                outputStream.write(requestPayload.getBytes("ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                outputStream.write(requestPayload.getBytes());
            }
            outputStream.flush();
        }
        byte[] bArr = new byte[1024];
        InputStream inputStream = this.mSocket.getInputStream();
        int readLineRN = ClientServerHello.readLineRN(inputStream, bArr);
        requestPayload = "";
        try {
            requestPayload = new String(bArr, 0, readLineRN, "ISO-8859-1");
        } catch (UnsupportedEncodingException e2) {
            requestPayload = new String(bArr, 0, readLineRN);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<strong>");
        stringBuilder.append(requestPayload);
        stringBuilder.append("</strong>");
        //SkStatus.logInfo(stringBuilder.toString());
        readLineRN = Integer.parseInt(requestPayload.substring(9, 12));
        if (readLineRN == 200) {
            return this.mSocket;
        }
        int readLineRN2;
        String valueOf = String.valueOf(readLineRN);
        String replace = requestPayload.replace(requestPayload, "HTTP/1.1 200 Ok");
        SkStatus.logInfo("Proxy: Auto Replace Header");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("<strong>");
        stringBuilder2.append(replace);
        stringBuilder2.append("</strong>");
        Integer.parseInt(valueOf.replace(valueOf, "200"));
        valueOf = requestPayload;
        while (true) {
            readLineRN2 = ClientServerHello.readLineRN(inputStream, bArr);
            if (readLineRN2 == 0) {
                break;
            }
            valueOf = new StringBuffer().append(valueOf).append("\n").toString();
            try {
                valueOf = new StringBuffer().append(valueOf).append(new String(bArr, 0, readLineRN2, "ISO-8859-1")).toString();
            } catch (UnsupportedEncodingException e3) {
                valueOf = new StringBuffer().append(valueOf).append(new String(bArr, 0, readLineRN2)).toString();
            }
        }
        if (!valueOf.isEmpty()) {
            SkStatus.logDebug(valueOf);
        }
        if (!requestPayload.startsWith("HTTP/")) {
            throw new NoClassDefFoundError("The proxy did not send back a valid HTTP response.");
        } else if (requestPayload.length() < 14) {
            throw new NoClassDefFoundError("The proxy did not send back a valid HTTP response.");
        } else if (requestPayload.charAt(8) != ' ') {
            throw new NoClassDefFoundError("The proxy did not send back a valid HTTP response.");
        } else if (requestPayload.charAt(12) != ' ') {
            throw new NoClassDefFoundError("The proxy did not send back a valid HTTP response.");
        } else if (readLineRN2 < 0 || readLineRN2 > 999) {
            throw new NoClassDefFoundError("The proxy did not send back a valid HTTP response.");
        } else if (readLineRN2 == 200) {
            return this.mSocket;
        } else {
            requestPayload = new StringBuffer().append(new StringBuffer().append("HTTP/1.0 200 Connection established").append("\r\n").toString()).append("\r\n").toString();
            outputStream.write(requestPayload.getBytes());
            outputStream.flush();
            SkStatus.logInfo(requestPayload.toString());
            return this.mSocket;
        }
    }

    private String getRequestPayload(String str, int i) {
        String str2 = this.requestPayload;
        if (str2 != null) {
            return TunnelUtils.formatCustomPayload(str, i, str2);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CONNECT ");
        stringBuffer.append(str);
        stringBuffer.append(':');
        stringBuffer.append(i);
        stringBuffer.append(" HTTP/1.0\r\n");
        if (!(this.proxyUser == null || this.proxyPass == null)) {
            char[] encode;
            str2 = new StringBuffer().append(new StringBuffer().append(this.proxyUser).append(":").toString()).append(this.proxyPass).toString();
            try {
                encode = Base64.encode(str2.getBytes("ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                encode = Base64.encode(str2.getBytes());
            }
            stringBuffer.append("Proxy-Authorization: Basic ");
            stringBuffer.append(encode);
            stringBuffer.append("\r\n");
        }
        stringBuffer.append("\r\n");
        return stringBuffer.toString();
    }

    private SSLSocket doSSLHandshake(String host, String sni, int port) throws IOException {
        try {
			TLSSocketFactory tsf = new TLSSocketFactory();
            SSLSocket socket = (SSLSocket) tsf.createSocket(host, port);
			try {
				socket.getClass().getMethod("setHostname", String.class).invoke(socket, sni);
				SkStatus.logInfo("Remote Proxy");
				SkStatus.logInfo("Setting up SNI..");
			} catch (Throwable e) {
				// ignore any error, we just can't set the hostname...
			}

			socket.setEnabledProtocols(socket.getSupportedProtocols());
            socket.addHandshakeCompletedListener(new HandshakeTunnelCompletedListener(host, port, socket));
            SkStatus.logInfo("Starting SSL Handshake...");
			socket.startHandshake();
			return socket;
        } catch (Exception e) {
            IOException iOException = new IOException(new StringBuffer().append("Could not do SSL handshake: ").append(e).toString());
            throw iOException;
        }
    }

    public SSLRemoteProxy(String str, int i, String str2, String str3) {
        this.stunnelServer = str;
        this.stunnelPort = i;
        this.stunnelHostSNI = str2;
        this.proxyUser = (String) null;
        this.proxyPass = (String) null;
        this.requestPayload = str3;
		this.sp = SocksHttpService.getSharedPrefs();
    }

    @Override
    public void close() {
        if (this.mSocket != null) {
            try {
                this.mSocket.close();
            } catch (IOException e) {
            }
        }
    }
}
