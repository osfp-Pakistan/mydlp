package com.mydlp.backend;

import java.net.InetSocketAddress;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mydlp.backend.thrift.Mydlp;
import com.mydlp.backend.thrift.MydlpImpl;

public class BackendServer {
	
	private static Logger logger = LoggerFactory.getLogger(BackendServer.class);
	
	protected TServerSocket serverTransport = null;
	
	@SuppressWarnings("unchecked")
	public void listen() {
		try {
			serverTransport = new TServerSocket(
					new InetSocketAddress("127.0.0.1", 9090));
			@SuppressWarnings("rawtypes")
			Mydlp.Processor processor = new Mydlp.Processor(new MydlpImpl());
			Factory protFactory = new TBinaryProtocol.Factory(true, true);
			TServer server = new TThreadPoolServer(new Args(serverTransport)
					.processor(processor).protocolFactory(protFactory));
			logger.debug("Starting server on port 9090.");
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		if (serverTransport != null)
			serverTransport.close();
	}

}
