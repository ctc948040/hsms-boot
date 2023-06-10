package com.hsms.config;

//public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
	public class GracefulShutdown  {

//    private static final Logger log = LoggerFactory.getLogger(GracefulShutdown.class);
//
//    private static final int TIMEOUT = 30;
//
//    private volatile Connector connector;
//
//    @Override
//    public void customize(Connector connector) {
//        this.connector = connector;
//    }
//
//    @Override
//    public void onApplicationEvent(ContextClosedEvent event) {
//        this.connector.pause();
//        Executor executor = this.connector.getProtocolHandler().getExecutor();
//        if (executor instanceof ThreadPoolExecutor) {
//            try {
//                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
//                threadPoolExecutor.shutdown();
//                if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
//                    log.warn("Tomcat thread pool did not shut down gracefully within "
//                            + TIMEOUT + " seconds. Proceeding with forceful shutdown");
//
//                    threadPoolExecutor.shutdownNow();
//
//                    if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
//                        log.error("Tomcat thread pool did not terminate");
//                    }
//                }
//            } catch (InterruptedException ex) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }

}