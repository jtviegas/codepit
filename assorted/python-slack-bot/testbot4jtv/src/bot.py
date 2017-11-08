import os
import time
from slackclient import SlackClient

# starterbot's ID as an environment variable
BOT_ID = os.environ.get("BOT_ID")

# constants
AT_BOT = "<@" + BOT_ID + ">"
EXAMPLE_COMMAND = "do"

def handle_command(command, channel):
    """
        Receives commands directed at the bot and determines if they
        are valid commands. If so, then acts on the commands. If not,
        returns back what it needs for clarification.
    """
    response = "Not sure what you mean. Use the *" + EXAMPLE_COMMAND + \
               "* command with numbers, delimited by spaces."
    if command.startswith(EXAMPLE_COMMAND):
        response = "Sure...write some more code then I can do that!"
    slack_client.api_call("chat.postMessage", channel=channel,
                          text=response, as_user=True)


def handle_channel_message(message):
    """
    handles messages sent to the channel, not direct to the bot, 
    as if we are listening to the channel.
    :return: None
    """
    channel = message['channel']
    response = "you typed: " + message['text']
    slack_client.api_call("chat.postMessage", channel=channel, text=response, as_user=True)

def handle_direct_message(message):
    """
    handles direct messages sent to the bot.
    :return: None
    """
    channel = message['channel']
    response = "you typed: " + message['text'].split(AT_BOT)[1]
    slack_client.api_call("chat.postMessage", channel=channel, text=response, as_user=True)

def parse_input(messageList):
    """
        The Slack Real Time Messaging API is an events firehose.
        this parsing function returns None unless a message is
        directed at the Bot, based on its ID.
    """
    if messageList and len(messageList) > 0:
        for message in messageList:
            if message and message['type'] == 'message' and 'text' in message and 'user' in message and message['user'] != BOT_ID:
                if AT_BOT in message['text']:
                    handle_direct_message(message)
                else:
                    handle_channel_message(message)


#{'user': 'U5TFF1S3A', 'text': 'hey', 'source_team': 'T5U7358QL', 'ts': '1497544336.174544', 'team': 'T5U7358QL', 'type': 'message', 'channel': 'C5TKA83S5'}
#{'ts': '1497546981.132406', 'user': 'U5TFF1S3A', 'team': 'T5U7358QL', 'text': '<@U5TJJ5WUR> howdy', 'source_team': 'T5U7358QL', 'channel': 'C5TKA83S5', 'type': 'message'}


# instantiate Slack & Twilio clients
slack_client = SlackClient(os.environ.get('SLACK_BOT_TOKEN'))
if __name__ == "__main__":
    READ_WEBSOCKET_DELAY = 1 # 1 second delay between reading from firehose
    if slack_client.rtm_connect():
        print("StarterBot connected and running!")
        while True:
            input = slack_client.rtm_read()
            if input and ( 0 < len(input) ):
                parse_input(input)

            time.sleep(READ_WEBSOCKET_DELAY)
    else:
        print("Connection failed. Invalid Slack token or bot ID?")