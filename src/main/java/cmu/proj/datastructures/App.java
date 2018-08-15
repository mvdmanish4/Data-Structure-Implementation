package cmu.proj.datastructures;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import cmu.proj.pojo.Input;
import cmu.proj.pojo.Result;
import cmu.proj.utils.Constants;


public class App {
	
	private final static Logger logger = Logger.getLogger(App.class);
	
	public static void main(String args[]){
		List<Input> inputList = new ArrayList<Input>();	
		
		Path path_input = new Constants().INPUT_10;
		Path path_output = new Constants().OUTPUT_10;
		
		try{
			
			int n = Integer.parseInt(Files.readAllLines(path_input).get(0));		
			Stream<String> lines = Files.lines(path_input).skip(1);
			lines.forEach(line -> {				
				List<String> values = Stream.of(line.split(" "))
			      .map (elem -> new String(elem))
			      .collect(Collectors.toList());
				inputList.add(new Input(Integer.parseInt(values.get(0)),Integer.parseInt(values.get(1))));
			  }
			);
			long result = maxTime(n,inputList);
			logger.info(result);
			Files.write(path_output, String.valueOf(result).getBytes());
			
		}catch(IOException e){
			logger.error("Error reading/writing the file");
			e.printStackTrace();
		}	
	}
	
	private static long maxTime(int n, List<Input> inputList){
		
		List<Result> resultList = new ArrayList<>();
		
		if(n == 0) return 0;		
		if(n == 1) return inputList.get(0).getEndTime() - inputList.get(0).getStartTime(); 
		
		inputList.sort(Comparator.comparing(Input::getStartTime));		
		
		for(int i=1;i<inputList.size()-1;i++){	
			int totalTime = inputList.get(i).getEndTime() - inputList.get(i).getStartTime();
			if((inputList.get(i).getStartTime()<inputList.get(i-1).getEndTime()) && !(inputList.get(i).getEndTime()>inputList.get(i+1).getStartTime())){
				
				if(inputList.get(i).getEndTime()<inputList.get(i-1).getEndTime())					
					resultList.add(new Result(new Input(inputList.get(i).getStartTime(), inputList.get(i).getStartTime()), totalTime, totalTime, 0));
				else{
					int overlapTime = inputList.get(i-1).getEndTime() - inputList.get(i).getStartTime();					
					resultList.add(new Result(new Input(inputList.get(i).getStartTime(), inputList.get(i).getStartTime()), totalTime, overlapTime, totalTime - overlapTime));}
			
			}else if((inputList.get(i).getStartTime()<inputList.get(i-1).getEndTime()) && (inputList.get(i).getEndTime()>inputList.get(i+1).getStartTime())){
				
				int overlapTime = (inputList.get(i-1).getEndTime() - inputList.get(i).getStartTime())+ (inputList.get(i).getEndTime() - inputList.get(i+1).getStartTime());
				resultList.add(new Result(new Input(inputList.get(i).getStartTime(),inputList.get(i).getEndTime()),totalTime,overlapTime,totalTime-overlapTime));
			
			}else if(!(inputList.get(i).getStartTime()<inputList.get(i-1).getEndTime()) && (inputList.get(i).getEndTime()>inputList.get(i+1).getStartTime())){
				
				int overlapTime = inputList.get(i).getEndTime() - inputList.get(i+1).getStartTime();
				resultList.add(new Result(new Input(inputList.get(i).getStartTime(),inputList.get(i).getEndTime()),totalTime,overlapTime,totalTime-overlapTime));
			
			}else resultList.add(new Result(new Input(inputList.get(i).getStartTime(),inputList.get(i).getEndTime()),totalTime,0,totalTime));
		}
		
		Result minRemainingTime = Collections.min(resultList, Comparator.comparing(resultValue -> resultValue.getRemainingTime()));
		inputList.removeIf(inputValue -> inputValue.equals(minRemainingTime.getVal()));
		
		long maxTimeCovered = 0;					
		for (Input input : inputList) {
			maxTimeCovered = maxTimeCovered + (input.getEndTime() - input.getStartTime());
		}
		
	 return maxTimeCovered;
	}

}
