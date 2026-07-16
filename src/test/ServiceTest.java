package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mvcVersion.CalculatorModel;

public class ServiceTest {
	private CalculatorModel model;
	
	@BeforeEach
	void setUp() {
		new CalculatorModel();
	}
	
	@Test
	void percentageTest() {
		
	}
}
