package messaging;

public class ReceiveMessage {

    public ReceiveMessage() {

    }

    public void receive(Message message) {
        // TODO: Check if message can be delivered
        // If my clock > buffer clock for my process id.
        // If so, deliver. Else, store in MessageBuffer
        // Then check for all other messages if they can be delivered

        deliver(message);
    }

    private void deliver(Message message) {
        // TODO: Deliver message.
        //deliver(m)	to	Pi
        //for	all (	(j,V’)	in	Sm )	do
        //if		(	there	exists	(j,V”)	in	S	)		then
        //	 	remove	(j,V”)	from	S
        //	 	V”:=max(V’,V”)
        //	 	insert(j,V”)	into	S
        //else	insert(j,V’)	into	S



        System.out.println(message.getMessage());
    }
}
