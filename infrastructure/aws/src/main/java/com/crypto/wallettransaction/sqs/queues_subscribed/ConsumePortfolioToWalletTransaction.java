package com.crypto.wallettransaction.sqs.queues_subscribed;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.Arn;
import software.amazon.awscdk.ArnComponents;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.sns.ITopic;
import software.amazon.awscdk.services.sns.Topic;
import software.amazon.awscdk.services.sns.subscriptions.SqsSubscription;
import software.amazon.awscdk.services.sqs.DeadLetterQueue;
import software.amazon.awscdk.services.sqs.Queue;
import software.constructs.Construct;

@Slf4j
public class ConsumePortfolioToWalletTransaction {
    private final Construct scope;
    private final StackProps props;

    public ConsumePortfolioToWalletTransaction(final Construct scope,
                                               final StackProps props) {
        this.scope = scope;
        this.props = props;
    }

    public void execute(){
        Queue queue = createQueue();
        ITopic topic = fetchTopic();

        executeSubscription(queue, topic);
    }

    private void executeSubscription(Queue queue, ITopic iTopic) {
        SqsSubscription sqsSubscription = SqsSubscription.Builder.create(queue).build();
        iTopic.addSubscription(sqsSubscription);
    }

    @NotNull
    private ITopic fetchTopic() {
        ArnComponents arnComponents = ArnComponents.builder()
                .account(props.getEnv().getAccount())
                .region(props.getEnv().getRegion())
                .partition("aws")
                .service("sns")
                .resource("wallet-manager-portfolio")
                .build();
        String arn = Arn.format(arnComponents);

        return Topic.fromTopicArn(scope, "wallet-manager-portfolio", arn);
    }

    @NotNull
    private Queue createQueue() {
        return Queue.Builder.create(scope, "wallet-manager-portfolio-to-wallet-transaction")
                .queueName("wallet-manager-portfolio-to-wallet-transaction")
                .visibilityTimeout(Duration.seconds(300))
                .deadLetterQueue(
                        DeadLetterQueue.builder()
                                .maxReceiveCount(3)
                                .queue(Queue.Builder.create(scope, "wallet-manager-portfolio-to-wallet-transaction-dlq")
                                        .queueName("wallet-manager-portfolio-to-wallet-transaction-dlq")
                                        .visibilityTimeout(Duration.seconds(600))
                                        .build())
                                .build()
                )
                .build();
    }
}
