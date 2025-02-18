package com.ubiqube.etsi.mano.alarm.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AlarmElementTest {

	@Test
	void test() {
		AlarmElement alarmElement = new AlarmElement();
		alarmElement.setMetric(null);
		assertEquals(null, alarmElement.getMetric());
		assertEquals(0, alarmElement.getTransforms().size());
		assertEquals(0, alarmElement.getAggregates().size());
	}

}
