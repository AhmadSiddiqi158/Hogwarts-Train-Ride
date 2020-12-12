public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

	public TrainNetwork(int nLines) {
		this.networkLines = new TrainLine[nLines];
	}

	public void addLines(TrainLine[] lines) {
		this.networkLines = lines;
	}

	public TrainLine[] getLines() {
		return this.networkLines;
	}

	public void dance() {
		System.out.println("The tracks are moving!");
		//YOUR CODE GOES HERE
		for(int i=0; i<this.getLines().length; i++) {
			this.getLines()[i].shuffleLine();
		}
	}

	public void undance() {
		//YOUR CODE GOES HERE
		for(int i=0; i<this.getLines().length; i++) {
			this.getLines()[i].sortLine();
		}
	}

	public int travel(String startStation, String startLine, String endStation, String endLine) {
		
		int hoursCount = 0;
		TrainLine curLine = this.getLineByName(startLine); //use this variable to store the current line.
		TrainStation curStation= curLine.findStation(startStation); //use this variable to store the current station.

		System.out.println("Departing from "+startStation);

		//YOUR CODE GOES HERE
		TrainStation previous=null;
		TrainStation next;
		
		if(startStation.equals(endStation)) {
			System.out.println("Arrived at destination after "+hoursCount+" hours!");
			return hoursCount;
		}
		while(true) {

			//prints an update on your current location in the network.

			if(hoursCount >= 168) {
				break;
			}
			
			if((hoursCount>0) && (hoursCount% swapFreq==0)) {
				this.dance();

			}
			
			next =curLine.travelOneStation(curStation, previous);
			previous=curStation;
			curStation=next;
			curLine= curStation.getLine();
			
			hoursCount ++;
			
			if (curStation.getName().equals(endStation)) {
				break;
			
			}
			System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
			System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
			System.out.println("=============================================");
		}

		System.out.println("Arrived at destination after "+hoursCount+" hours!");
		return hoursCount;
	}


	//you can extend the method header if needed to include an exception. You cannot make any other change to the header.
	public TrainLine getLineByName(String lineName){
		//YOUR CODE GOES HERE
		for(int i=0; i<this.getLines().length; i++){
			if(this.networkLines[i].getName().equals(lineName)) {
				return this.networkLines[i];
			}
		}

		throw new LineNotFoundException("line not found");
	}

	//prints a plan of the network for you.
	public void printPlan() {
		System.out.println("CURRENT TRAIN NETWORK PLAN");
		System.out.println("----------------------------");
		for(int i=0;i<this.networkLines.length;i++) {
			System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
		}
		System.out.println("----------------------------");
	}
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	String name;

	public LineNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "LineNotFoundException[" + name + "]";
	}
}