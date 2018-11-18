package messaging;

import java.util.Scanner;

public class MessageSender {

    private MessageCenter msgCenter;

    public MessageSender(MessageCenter msgCenter) {
        this.msgCenter = msgCenter;
    }

    /**
     * Starts a scanner that takes input, which is used to send message.
     */
    public void startSender() {
        Scanner scan = new Scanner(System.in);
        try {
            int msgCounter = 0;
            while (scan.hasNextLine()) {
                msgCounter++;
                int toProcess = scan.nextInt();
//                String receiver = scan.next();
                int delay = scan.nextInt();
                msgCenter.sendMessage(toProcess, "localhost", "Message number " + msgCounter, delay);
            }
        } finally {
            scan.close();
        }
    }
}
