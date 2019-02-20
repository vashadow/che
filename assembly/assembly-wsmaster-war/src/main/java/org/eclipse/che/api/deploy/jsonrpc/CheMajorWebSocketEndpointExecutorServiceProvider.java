/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.deploy.jsonrpc;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.eclipse.che.commons.lang.concurrent.LoggingUncaughtExceptionHandler;
import org.slf4j.Logger;

/**
 * {@link java.util.concurrent.ExecutorService} provider used in {@link
 * org.eclipse.che.api.deploy.jsonrpc.CheMajorWebSocketEndpoint}
 * `che.core.jsonrpc.processor_core_pool_size` - corePoolSize of ThreadPoolExecutor
 * `che.core.jsonrpc.processor_max_pool_size` - maximumPoolSize of ThreadPoolExecutor
 * `che.core.jsonrpc.processor_queue_capacity` - >0 capacity of @{@link
 * java.util.concurrent.LinkedBlockingQueue}, <=0 {@link java.util.concurrent.SynchronousQueue} are
 * used.
 */
@Singleton
public class CheMajorWebSocketEndpointExecutorServiceProvider implements Provider<ExecutorService> {

  private static final Logger LOG =
      getLogger(CheMajorWebSocketEndpointExecutorServiceProvider.class);

  private final ThreadPoolExecutor executor;

  @Inject
  public CheMajorWebSocketEndpointExecutorServiceProvider(
      @Named("che.core.jsonrpc.processor_core_pool_size") int corePoolSize,
      @Named("che.core.jsonrpc.processor_max_pool_size") int maxPoolSize,
      @Named("che.core.jsonrpc.processor_queue_capacity") int queueCapacity) {
    ThreadFactory factory =
        new ThreadFactoryBuilder()
            .setUncaughtExceptionHandler(LoggingUncaughtExceptionHandler.getInstance())
            .setNameFormat(CheMajorWebSocketEndpoint.class.getSimpleName() + "-%d")
            .setDaemon(true)
            .build();

    executor =
        new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            60L,
            SECONDS,
            queueCapacity > 0 ? new LinkedBlockingQueue<>(queueCapacity) : new SynchronousQueue<>(),
            factory);
    executor.setRejectedExecutionHandler(
        (r, __) -> LOG.error("Message {} rejected for execution", r));
    executor.prestartCoreThread();
  }

  @Override
  public ExecutorService get() {
    return executor;
  }
}