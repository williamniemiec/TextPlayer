import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;

import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;


/**
 * Main class. Responsible for running the application.
 */
public class Main 
{
	public static void main(String[] args) 
	{
		/*Controller c = new HomeController();
		c.run();
		*/
		Player player = new Player();
	    //player.play("C D E F G A B");
		Sequence sequence = player.getSequence("C D E F G A B");
		
		ManagedPlayer mp = new ManagedPlayer();
		try {
			mp.start(sequence);
			mp.pause();
			mp.resume();
			
			// Comprimento
			System.out.println(mp.getTickLength());
			while (!mp.isFinished()) {
				System.out.println(mp.getTickPosition());	// posição atual
			}
			
		} catch (InvalidMidiDataException | MidiUnavailableException e) {
			e.printStackTrace();
		}
	    
	}
}
