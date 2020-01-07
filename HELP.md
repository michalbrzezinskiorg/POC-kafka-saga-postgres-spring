# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/maven-plugin/)
* [Apache Kafka Streams Support](https://docs.spring.io/spring-kafka/docs/current/reference/html/_reference.html#kafka-streams)
* [Apache Kafka Streams Binding Capabilities of Spring Cloud Stream](https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/#_kafka_streams_binding_capabilities_of_spring_cloud_stream)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Jersey](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-jersey)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-kafka)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Samples for using Apache Kafka Streams with Spring Cloud stream](https://github.com/spring-cloud/spring-cloud-stream-samples/tree/master/kafka-streams-samples)


### properties for Kafka
* REQUIRED *
KAFKA_BROKER_ID	broker.id
KAFKA_LOG_DIRS	log.dirs
KAFKA_ZK_CONNECT	zookeeper.connect
* OPTIONAL, SET BY DEFAULT *
KAFKA_PORT	port	9092
KAFKA_NUM_PARTITIONS	num.partitions	1
KAFKA_AUTO_CREATE_TOPICS_ENABLE	auto.create.topics.enable	false
KAFKA_DEFAULT_REPLICATION_FACTOR	default.replication.factor	1
KAFKA_MESSAGE_MAX_BYTES	message.max.bytes	20971520
KAFKA_REPLICA_FETCH_MAX_BYTES	replica.fetch.max.bytes	20971520
* OPTIONAL, NOT SET BY DEFAULT *
KAFKA_ADVERTISED_HOST_NAME	advertised.host.name	kafka1
KAFKA_ADVERTISED_LISTENERS	advertised.listeners	
KAFKA_ADVERTISED_PORT	advertised.port	9092
KAFKA_AUTHORIZER_CLASS_NAME	authorizer.class.name	
KAFKA_AUTO_LEADER_REBALANCE_ENABLE	auto.leader.rebalance.enable	true
KAFKA_BACKGROUND_THREADS	background.threads	10
KAFKA_COMPRESSION_TYPE	compression.type	producer
KAFKA_CONNECTIONS_MAX_IDLE_MS	connections.max.idle.ms	600000
KAFKA_CONTROLLED_SHUTDOWN_ENABLE	controlled.shutdown.enable	true
KAFKA_CONTROLLED_SHUTDOWN_MAX_RETRIES	controlled.shutdown.max.retries	3
KAFKA_CONTROLLED_SHUTDOWN_RETRY_BACKOFF_MS	controlled.shutdown.retry.backoff.ms	5000
KAFKA_CONTROLLER_SOCKET_TIMEOUT_MS	controller.socket.timeout.ms	30000
KAFKA_DELETE_TOPIC_ENABLE	delete.topic.enable	false
KAFKA_FETCH_PURGATORY_PURGE_INTERVAL_REQUESTS	fetch.purgatory.purge.interval.requests	1000
KAFKA_GROUP_MAX_SESSION_TIMEOUT_MS	group.max.session.timeout.ms	30000
KAFKA_GROUP_MIN_SESSION_TIMEOUT_MS	group.min.session.timeout.ms	6000
KAFKA_HOST_NAME	host.name	0.0.0.0
KAFKA_INTER_BROKER_PROTOCOL_VERSION	inter.broker.protocol.version	0.9.0.X
KAFKA_LISTENERS	listeners	PLAINTEXT://:9092
KAFKA_LOG_CLEANER_BACKOFF_MS	log.cleaner.backoff.ms	15000
KAFKA_LOG_CLEANER_DEDUPE_BUFFER_SIZE	log.cleaner.dedupe.buffer.size	524288000
KAFKA_LOG_CLEANER_DELETE_RETENTION_MS	log.cleaner.delete.retention.ms	86400000
KAFKA_LOG_CLEANER_ENABLE	log.cleaner.enable	false
KAFKA_LOG_CLEANER_ENABLE	log.cleaner.enable	false
KAFKA_LOG_CLEANER_IO_BUFFER_LOAD_FACTOR	log.cleaner.io.buffer.load.factor	0.9
KAFKA_LOG_CLEANER_IO_BUFFER_SIZE	log.cleaner.io.buffer.size	524288
KAFKA_LOG_CLEANER_IO_MAX_BYTES_PER_SECOND	log.cleaner.io.max.bytes.per.second	1.7976931348623157E308
KAFKA_LOG_CLEANER_MIN_CLEANABLE_RATIO	log.cleaner.min.cleanable.ratio	0.5
KAFKA_LOG_CLEANER_THREADS	log.cleaner.threads	1
KAFKA_LOG_CLEANUP_POLICY	log.cleanup.policy	delete
KAFKA_LOG_FLUSH_INTERVAL_MESSAGES	log.flush.interval.messages	9223372036854775807
KAFKA_LOG_FLUSH_INTERVAL_MS	log.flush.interval.ms	
KAFKA_LOG_FLUSH_OFFSET_CHECKPOINT_INTERVAL_MS	log.flush.offset.checkpoint.interval.ms	60000
KAFKA_LOG_FLUSH_SCHEDULER_INTERVAL_MS	log.flush.scheduler.interval.ms	9223372036854775807
KAFKA_LOG_INDEX_INTERVAL_BYTES	log.index.interval.bytes	4096
KAFKA_LOG_INDEX_SIZE_MAX_BYTES	log.index.size.max.bytes	10485760
KAFKA_LOG_PREALLOCATE	log.preallocate	false
KAFKA_LOG_RETENTION_BYTES	log.retention.bytes	-1
KAFKA_LOG_RETENTION_CHECK_INTERVAL_MS	log.retention.check.interval.ms	300000
KAFKA_LOG_RETENTION_CHECK_INTERVAL_MS	log.retention.check.interval.ms	300000
KAFKA_LOG_RETENTION_HOURS	log.retention.hours	168
KAFKA_LOG_RETENTION_MINUTES	log.retention.minutes	
KAFKA_LOG_RETENTION_MS	log.retention.ms	
KAFKA_LOG_ROLL_HOURS	log.roll.hours	168
KAFKA_LOG_ROLL_JITTER_HOURS	log.roll.jitter.hours	0
KAFKA_LOG_ROLL_JITTER_MS	log.roll.jitter.ms	
KAFKA_LOG_ROLL_MS	log.roll.ms	
KAFKA_LOG_SEGMENT_BYTES	log.segment.bytes	1073741824
KAFKA_LOG_SEGMENT_BYTES	log.segment.bytes	1073741824
KAFKA_LOG_SEGMENT_DELETE_DELAY_MS	log.segment.delete.delay.ms	60000
KAFKA_MAX_CONNECTIONS_PER_IP_OVERRIDES	max.connections.per.ip.overrides	
KAFKA_MAX_CONNECTIONS_PER_IP	max.connections.per.ip	2147483647
KAFKA_METRIC_REPORTERS	metric.reporters	[]
KAFKA_METRICS_NUM_SAMPLES	metrics.num.samples	2
KAFKA_METRICS_SAMPLE_WINDOW_MS	metrics.sample.window.ms	30000
KAFKA_MIN_INSYNC_REPLICAS	min.insync.replicas	1
KAFKA_NUM_IO_THREADS	num.io.threads	8
KAFKA_NUM_IO_THREADS	num.io.threads	8
KAFKA_NUM_NETWORK_THREADS	num.network.threads	3
KAFKA_NUM_NETWORK_THREADS	num.network.threads	3
KAFKA_NUM_PARTITIONS	num.partitions	1
KAFKA_NUM_RECOVERY_THREADS_PER_DATA_DIR	num.recovery.threads.per.data.dir	1
KAFKA_NUM_RECOVERY_THREADS_PER_DATA_DIR	num.recovery.threads.per.data.dir	1
KAFKA_NUM_REPLICA_FETCHERS	num.replica.fetchers	1
KAFKA_OFFSET_METADATA_MAX_BYTES	offset.metadata.max.bytes	4096
KAFKA_OFFSETS_COMMIT_REQUIRED_ACKS	offsets.commit.required.acks	-1
KAFKA_OFFSETS_COMMIT_TIMEOUT_MS	offsets.commit.timeout.ms	5000
KAFKA_OFFSETS_LOAD_BUFFER_SIZE	offsets.load.buffer.size	5242880
KAFKA_OFFSETS_RETENTION_CHECK_INTERVAL_MS	offsets.retention.check.interval.ms	600000
KAFKA_OFFSETS_RETENTION_MINUTES	offsets.retention.minutes	1440
KAFKA_OFFSETS_TOPIC_COMPRESSION_CODEC	offsets.topic.compression.codec	0
KAFKA_OFFSETS_TOPIC_NUM_PARTITIONS	offsets.topic.num.partitions	50
KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR	offsets.topic.replication.factor	3
KAFKA_OFFSETS_TOPIC_SEGMENT_BYTES	offsets.topic.segment.bytes	104857600
KAFKA_PRINCIPAL_BUILDER_CLASS	principal.builder.class	org.apache.kafka.common.security.auth.DefaultPrincipalBuilder
KAFKA_PRODUCER_PURGATORY_PURGE_INTERVAL_REQUESTS	producer.purgatory.purge.interval.requests	1000
KAFKA_QUEUED_MAX_REQUESTS	queued.max.requests	500
KAFKA_QUOTA_CONSUMER_DEFAULT	quota.consumer.default	9223372036854775807
KAFKA_QUOTA_PRODUCER_DEFAULT	quota.producer.default	9223372036854775807
KAFKA_QUOTA_WINDOW_NUM	quota.window.num	11
KAFKA_QUOTA_WINDOW_SIZE_SECONDS	quota.window.size.seconds	1
KAFKA_REPLICA_FETCH_BACKOFF_MS	replica.fetch.backoff.ms	1000
KAFKA_REPLICA_FETCH_MIN_BYTES	replica.fetch.min.bytes	1
KAFKA_REPLICA_FETCH_WAIT_MAX_MS	replica.fetch.wait.max.ms	500
KAFKA_REPLICA_HIGH_WATERMARK_CHECKPOINT_INTERVAL_MS	replica.high.watermark.checkpoint.interval.ms	5000
KAFKA_REPLICA_LAG_TIME_MAX_MS	replica.lag.time.max.ms	10000
KAFKA_REPLICA_SOCKET_RECEIVE_BUFFER_BYTES	replica.socket.receive.buffer.bytes	65536
KAFKA_REPLICA_SOCKET_TIMEOUT_MS	replica.socket.timeout.ms	30000
KAFKA_REQUEST_TIMEOUT_MS	request.timeout.ms	30000
KAFKA_RESERVED_BROKER_MAX_ID	reserved.broker.max.id	1000
KAFKA_SASL_KERBEROS_KINIT_CMD	sasl.kerberos.kinit.cmd	/usr/bin/kinit
KAFKA_SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN	sasl.kerberos.min.time.before.relogin	60000
KAFKA_SASL_KERBEROS_PRINCIPAL_TO_LOCAL_RULES	sasl.kerberos.principal.to.local.rules	[DEFAULT]
KAFKA_SASL_KERBEROS_SERVICE_NAME	sasl.kerberos.service.name	
KAFKA_SASL_KERBEROS_TICKET_RENEW_JITTER	sasl.kerberos.ticket.renew.jitter	0.05
KAFKA_SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR	sasl.kerberos.ticket.renew.window.factor	0.8
KAFKA_SECURITY_INTER_BROKER_PROTOCOL	security.inter.broker.protocol	PLAINTEXT
KAFKA_SOCKET_RECEIVE_BUFFER_BYTES	socket.receive.buffer.bytes	102400
KAFKA_SOCKET_RECEIVE_BUFFER_BYTES	socket.receive.buffer.bytes	102400
KAFKA_SOCKET_REQUEST_MAX_BYTES	socket.request.max.bytes	104857600
KAFKA_SOCKET_REQUEST_MAX_BYTES	socket.request.max.bytes	104857600
KAFKA_SOCKET_SEND_BUFFER_BYTES	socket.send.buffer.bytes	102400
KAFKA_SOCKET_SEND_BUFFER_BYTES	socket.send.buffer.bytes	102400
KAFKA_SSL_CIPHER_SUITES	ssl.cipher.suites	
KAFKA_SSL_CLIENT_AUTH	ssl.client.auth	none
KAFKA_SSL_ENABLED_PROTOCOLS	ssl.enabled.protocols	[TLSv1.2]
KAFKA_SSL_ENDPOINT_IDENTIFICATION_ALGORITHM	ssl.endpoint.identification.algorithm	
KAFKA_SSL_KEY_PASSWORD	ssl.key.password	
KAFKA_SSL_KEYMANAGER_ALGORITHM	ssl.keymanager.algorithm	SunX509
KAFKA_SSL_KEYSTORE_LOCATION	ssl.keystore.location	
KAFKA_SSL_KEYSTORE_PASSWORD	ssl.keystore.password	
KAFKA_SSL_KEYSTORE_TYPE	ssl.keystore.type	JKS
KAFKA_SSL_PROTOCOL	ssl.protocol	TLS
KAFKA_SSL_PROVIDER	ssl.provider	
KAFKA_SSL_TRUSTMANAGER_ALGORITHM	ssl.trustmanager.algorithm	PKIX
KAFKA_SSL_TRUSTSTORE_LOCATION	ssl.truststore.location	
KAFKA_SSL_TRUSTSTORE_PASSWORD	ssl.truststore.password	
KAFKA_SSL_TRUSTSTORE_TYPE	ssl.truststore.type	JKS
KAFKA_UNCLEAN_LEADER_ELECTION_ENABLE	unclean.leader.election.enable	true
KAFKA_ZOOKEEPER_CONNECTION_TIMEOUT_MS	zookeeper.connection.timeout.ms	
KAFKA_ZOOKEEPER_SESSION_TIMEOUT_MS	zookeeper.session.timeout.ms	6000
KAFKA_ZOOKEEPER_SET_ACL	zookeeper.set.acl	false
KAFKA_ZOOKEEPER_SYNC_TIME_MS	zookeeper.sync.time.ms