/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.alarm.entities.alarm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.alarm.ModelTest;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.AggregatesDto;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.AlarmDto;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.MetricsDto;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.TransformDto;

/**
 *
 * @author Olivier Vignaud
 *
 */
@SuppressWarnings("static-method")
class ObjectTest {

	@Test
	void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		ModelTest.realHandle(AggregatesDto.class.getName());
		ModelTest.realHandle(AlarmDto.class.getName());
		ModelTest.realHandle(MetricsDto.class.getName());
		ModelTest.realHandle(TransformDto.class.getName());
		assertTrue(true);
	}

}
