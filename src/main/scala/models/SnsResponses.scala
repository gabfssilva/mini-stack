package models

import java.util.UUID

object SnsResponses:
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
    <DeleteTopicResponse xmlns="http://sns.amazonaws.com/doc/2010-03-31/">
      <ResponseMetadata>
        <RequestId>{UUID.randomUUID}</RequestId>
      </ResponseMetadata>
    </DeleteTopicResponse>

