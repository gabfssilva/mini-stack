package models

import java.util.UUID

object SnsResponses:
  def subscriptionResponse(subscription: Subscription) =
   <SubscribeResponse>
      <SubscribeResult>
          <SubscriptionArn>{subscription.arn}</SubscriptionArn>
      </SubscribeResult>
      <ResponseMetadata>
          <RequestId>{UUID.randomUUID()}</RequestId>
      </ResponseMetadata>
    </SubscribeResponse>

  def publishResponse =
    <PublishResponse>
      <PublishResult>
          <MessageId>{UUID.randomUUID()}</MessageId>
      </PublishResult>
      <ResponseMetadata>
          <RequestId>{UUID.randomUUID()}</RequestId>
      </ResponseMetadata>
    </PublishResponse> 

  def listTopicsResponse(topics: Set[Topic]) =
    <ListTopicsResponse>
      <ListTopicsResult>{topics.map:
        (topic: Topic) =>
          <Topic>
            <member>
              <TopicArn>{topic.arn}</TopicArn>
            </member>
          </Topic>}
      </ListTopicsResult>

      <ResponseMetadata>
        <RequestId>{UUID.randomUUID()}</RequestId>
      </ResponseMetadata>
    </ListTopicsResponse>

  def createTopicResponse(topic: Topic) =
    <CreateTopicResponse>
      <ResponseMetadata>
        <RequestId>{UUID.randomUUID()}</RequestId>
      </ResponseMetadata>

      <CreateTopicResult>
        <TopicArn>{topic.arn}</TopicArn>
      </CreateTopicResult>
    </CreateTopicResponse>

  def deleteTopicResponse =
    <DeleteTopicResponse>
      <ResponseMetadata>
        <RequestId>{UUID.randomUUID}</RequestId>
      </ResponseMetadata>
    </DeleteTopicResponse>

