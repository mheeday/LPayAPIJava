package com.lannisterpay.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ApiApplication {

	Map <String, Object> result = new LinkedHashMap <String, Object> ();



	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}


	@PostMapping(path = "split-payments/compute")
	Map <String, Object> computePayment(@RequestBody Map <String, Object> in_json) {

		ArrayList arr_list = (ArrayList) in_json.get("SplitInfo");

		result.put("ID", in_json.get("ID"));
		result.put("Balance", (int) in_json.get("Amount"));
		result.put("SplitBreakdown", new ArrayList<LinkedHashMap>()); // new Object[in_json.get("SplitBreakdown").length]
		result.put("ratioCount", 0);


		flatProcessor(arr_list, result);
 		percentProcessor(arr_list, result);
		ratioProcessor(arr_list, result);

		return result;

	}


	static void flatProcessor (ArrayList arr_list, Map <String, Object> result) {
		int new_balance = (int) result.get("Balance");
		for (int i = 0; i < arr_list.size(); i++) {
			LinkedHashMap lhm = (LinkedHashMap) arr_list.get(i);

			if (lhm.get("SplitType").equals("FLAT")) {
				new_balance = new_balance - (int) lhm.get("SplitValue");
				result.put("Balance", new_balance);
				LinkedHashMap <String, Object> temp_lhm = new LinkedHashMap <String, Object> ();
				temp_lhm.put("SplitEntityId", lhm.get("SplitEntityId"));
				temp_lhm.put("Amount", lhm.get("SplitValue"));

				((ArrayList) result.get("SplitBreakdown")).add(temp_lhm);

			} else if (lhm.get("SplitType").equals("RATIO")) {
				int oldRatio = (int) (result.get("ratioCount"));
				int rVal = (int) lhm.get("SplitValue");
				result.put("ratioCount", oldRatio+rVal);
			}
		}
		
	} 
 	static void percentProcessor (ArrayList arr_list, Map <String, Object> result) {
		int new_b = (int) result.get("Balance");
		double new_balance = (double) (new_b);
		for (int i = 0; i < arr_list.size(); i++) {
			LinkedHashMap lhm = (LinkedHashMap) arr_list.get(i);

			if (lhm.get("SplitType").equals("PERCENTAGE")) {
				int sVal = (int) lhm.get("SplitValue");
				double splitValue = (double) (sVal);
				double add_amount = (splitValue/100) * new_balance;
				new_balance = new_balance - add_amount;
				result.put("Balance", new_balance);
				LinkedHashMap <String, Object> temp_lhm = new LinkedHashMap <String, Object> ();
				temp_lhm.put("SplitEntityId", lhm.get("SplitEntityId"));
				temp_lhm.put("Amount", add_amount);

				((ArrayList) result.get("SplitBreakdown")).add(temp_lhm);
			}
		}

} 
	static void ratioProcessor (ArrayList arr_list, Map <String, Object> result) {
		//int new_b = (int) result.get("Balance");
		double new_balance =  (double )(result.get("Balance"));
		int ratio = (int) (result.get("ratioCount"));
		if (ratio > 0) {
			for (int i = 0; i < arr_list.size(); i++) {
				LinkedHashMap lhm = (LinkedHashMap) arr_list.get(i);
	
				if (lhm.get("SplitType").equals("RATIO")) {
					int sVal = (int) lhm.get("SplitValue");
					double splitValue = (double) (sVal);
					double add_amount = (splitValue/ratio) * new_balance;
					LinkedHashMap <String, Object> temp_lhm = new LinkedHashMap <String, Object> ();
					temp_lhm.put("SplitEntityId", lhm.get("SplitEntityId"));
					temp_lhm.put("Amount", add_amount);
	
					((ArrayList) result.get("SplitBreakdown")).add(temp_lhm);
				}
			}
			result.put("Balance", 0);
		}
		//result.remove("ratioCount");
	}
}
