import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	//public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
			 * Constructor for TrainStation. input: stationNames - An array of String
			 * containing the name of the stations to be placed in the line name - Name of
			 * the line goingRight - boolean indicating the direction of travel
			 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {

		// YOUR CODE GOES HERE
		int numStations= 1;
		TrainStation start= this.leftTerminus;
		while(!(start.isRightTerminal())) {
			numStations++;
			start= start.getRight();
		}
		return numStations; // change this!
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}


	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {

		// YOUR CODE GOES HERE


		if ((current.hasConnection) && !(current.getTransferStation().equals(previous))) {
			return current.getTransferStation();
		}
		return this.getNext(current);

	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) {

		// YOUR CODE GOES HERE
		this.findStation(station.getName());
		if (this.goingRight) {
			if (station.isRightTerminal()) {
				this.reverseDirection();
				return station.getLeft();
			}
			else return station.getRight();	
		}
		if(!this.goingRight) {
			if (station.isLeftTerminal()) {
				this.reverseDirection();
				return station.getRight();
			}
			else 
				return station.getLeft();
		}
		else {
			throw new StationNotFoundException(station.getName());
		}

	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) {

		// YOUR CODE GOES HERE
		TrainStation current= this.leftTerminus;
		while(!(current.isRightTerminal())) {

			if (current.getName().equals(name)) 
				return current;
			current= current.getRight();
		}
	
		boolean found= current.getName().equals(name);
		if (found==true) return current;
		
		throw new StationNotFoundException("Station not found");

	}

	public void swap(TrainStation first, TrainStation second) {

		first.setRight(second.getRight());

		if(first.getLeft()!=null) {
			first.getLeft().setRight(second);
		}

		second.setLeft(first.getLeft());

		if(second.getRight() != null) {
			second.getRight().setLeft(first);
		}    
		first.setRight(second.getRight());

		second.setRight(first);
		first.setLeft(second);

		second.setNonTerminal();
		first.setNonTerminal();
	}

	public void sortLine() {

		// YOUR CODE GOES HERE

		boolean sorted=true;

		for(int i=0;i<this.getSize()-1; i++) {

			//System.out.println("\n\n");
			TrainStation temp= this.leftTerminus;


			for (int j=0; j<this.getSize()-i-1; j++) {
				TrainStation tempNext=temp.getRight();
				sorted=true;

				//System.out.println("temp: "+temp.getName()+ "  tempNext  "+ tempNext.getName());

				if (temp.getName().compareTo(tempNext.getName())>0) {

					if(temp.getLeft()==null) {
						swap(temp,tempNext);
						tempNext.setLeftTerminal();
						this.leftTerminus=tempNext;
						tempNext.setLeft(null);
						sorted=false;
					}


					else if(tempNext.getRight()==null){
						swap(temp,tempNext);
						temp.setRightTerminal();
						this.rightTerminus=temp;
						temp.setRight(null);
						sorted=false;
					}
					else {

						swap(temp,tempNext);
						sorted=false;
					}
					//temp=tempNext;

					//System.out.println(temp.getName());
				}
				else if (temp.getName().compareTo(tempNext.getName())<0) {
					temp=tempNext;
					sorted=false;
				}
				//System.out.println(this.toString());
			}
			if(sorted==true) 
				break;
		}

		this.lineMap=this.getLineArray();

	}



	public TrainStation[] getLineArray() {

		// YOUR CODE GOES HERE
		TrainStation[] Map= new TrainStation[this.getSize()];
		TrainStation current= this.leftTerminus;
		for(int i=0; i< this.getSize();i++) {
			Map[i]=current;
			current=current.getRight();
		}
		return Map; 
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);

		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		
		this.getLeftTerminus().setNonTerminal();;  
		this.getRightTerminus().setNonTerminal();; 
		/*
		 * for(int i=0; i<lineMap.length;i++) {
		 * System.out.println(lineMap[i].getName()); }
		 */
		TrainStation[] shuffledArray = shuffleArray(lineArray);

		//System.out.print(shuffledArray.toString());

		this.rightTerminus=shuffledArray[shuffledArray.length-1];
		this.rightTerminus.setRight(null);
		this.rightTerminus.setLeft(shuffledArray[shuffledArray.length-2]);
		shuffledArray[shuffledArray.length-1].setRightTerminal(); 
		this.rightTerminus.setTrainLine(this); 

		this.leftTerminus=shuffledArray[0] ; 
		this.leftTerminus.setLeft(null);
		this.leftTerminus.setRight(shuffledArray[1]); 

		shuffledArray[0].setLeftTerminal();   	
		this.leftTerminus.setLeftTerminal();
		this.leftTerminus.setTrainLine(this);


		for(int i=1; i<shuffledArray.length-1; i++) {
			//this.addStation(shuffledArray[i]);
			shuffledArray[i].setNonTerminal();
			TrainStation current= shuffledArray[i]; 
			current.setLeft(shuffledArray[i-1]);
			current.setRight(shuffledArray[i+1]);

		}
		/*
		 * for(int i=0; i<lineMap.length;i++) {
		 * System.out.println(lineMap[i].getName()); }
		 */

		this.lineMap=shuffledArray;
	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
