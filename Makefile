DEFAULT_GOAL := help

.PHONY: help
help: ## Show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

run-test: ## run all test, unit | integration | e2e
	mvn clean test

run-build:
	mvn clean install

aws-list-topics: ## list aws topics
	awslocal sns list-topics

aws-list-queue: ## list aws queues
	awslocal sqs list-queues

aws-create-topic: ## create a topic ex: $ aws-create-topic topic=my_topic_name
	awslocal sns create-topic --name $(topic)

aws-create-queue: ## create a topic ex: $ aws-create-queue queue=my_queue_name
	awslocal sqs create-queue --queue-name $(queue)

aws-subscribe: ## create a subscriber between topic and queue $ aws-subscribe topic_arn=arn:aws:sns:us-east-1:000000000000:topic_name queue_arn=arn:aws:sqs:us-east-1:000000000000:queue_name
	awslocal sns subscribe --protocol sqs --topic-arn $(topic_arn) --notification-endpoint $(queue_arn)

aws-receive-message: ## receive msg by queue-url $ aws-receive-message=http://localhost:4566/000000000000/wallet-trade-calculator
	awslocal sqs receive-message --queue-url $(queue_url)

infra-docker-local-start: ## Run infrastructure locally
	docker-compose -f infrastructure/monitoring/docker-compose.yml up -d --build

infra-docker-local-stop: ## Stop infrastructure locally
	docker-compose -f infrastructure/monitoring/docker-compose.yml down

infra-podman-local-start: ## Run infrastructure locally
	podman-compose -f infrastructure/monitoring/docker-compose.yml up -d --build

infra-podman-local-stop: ## Stop infrastructure locally
	podman-compose -f infrastructure/monitoring/docker-compose.yml down