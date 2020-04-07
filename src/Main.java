import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.*;
import controllers.HomeController;


/**
 * Main class. Responsible for running the application.
 */
public class Main 
{
	public static void main(String[] args) 
	{
		/*
		Controller c = new HomeController();
		c.run();
		*/
		
        
		
       // Controller controller = new Controller();
		Player player = new Player();
		//Pattern pattern = new Pattern(":CON(7, 40) C D E F G A B");
		Pattern pattern = new Pattern("C @1 D E F G");
		//Pattern pattern = new Pattern("T[Grave] V0 I0 G6q A5q V1 A5q G6q");
		//Pattern pattern = new Pattern(" :CON(7, 10) C D E F G A B");
		player.play(pattern);
	    //player.play("C D E F G A B");
		//Sequence sequence = player.getSequence("C D E F G A B");
		//Sequence sequence = player.getSequence("X[Volume]= C D E O+ O+F G A B");
		/*
		ManagedPlayer mp = new ManagedPlayer();
		try {
			mp.start(sequence);
			
			
			mp.pause();
			mp.resume();
			
			// Comprimento
			System.out.println(mp.getTickLength());
			while (!mp.isFinished()) {
				System.out.println(mp.getTickPosition());	// posi��o atual
			}
			
		} catch (InvalidMidiDataException | MidiUnavailableException e) {
			e.printStackTrace();
		}
	    */
	}
}
