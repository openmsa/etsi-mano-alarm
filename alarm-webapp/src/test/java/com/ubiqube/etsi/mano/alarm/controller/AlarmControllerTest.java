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
package com.ubiqube.etsi.mano.alarm.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.AlarmDto;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.SubscriptionDto;
import com.ubiqube.etsi.mano.alarm.service.AlarmService;
import com.ubiqube.etsi.mano.alarm.service.mapper.AggregatesMapper;
import com.ubiqube.etsi.mano.alarm.service.mapper.AuthenticationMapper;
import com.ubiqube.etsi.mano.alarm.service.mapper.MetricMapper;
import com.ubiqube.etsi.mano.alarm.service.mapper.TransformMapper;

@ExtendWith(MockitoExtension.class)
class AlarmControllerTest {
	@Mock
	private AlarmService alarmService;
	private final TransformMapper transformMapper = Mappers.getMapper(TransformMapper.class);
	private final MetricMapper metricMapper = Mappers.getMapper(MetricMapper.class);
	private final AuthenticationMapper authenticationMapper = Mappers.getMapper(AuthenticationMapper.class);
	private final AggregatesMapper aggregatesMapper = Mappers.getMapper(AggregatesMapper.class);

	@Test
	void testCreateAlarm() {
		final AlarmController srv = createService();
		final AlarmDto alarm = new AlarmDto();
		final SubscriptionDto subs = new SubscriptionDto();
		subs.setCallbackUri(URI.create("http://localhost/"));
		alarm.setSubscription(subs);
		srv.createAlarm(alarm);
		assertTrue(true);
	}

	private AlarmController createService() {
		return new AlarmController(alarmService, transformMapper, metricMapper, authenticationMapper, aggregatesMapper);
	}

	@Test
	void testDeleteAlarm() {
		final AlarmController srv = createService();
		srv.deleteAlaram(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final AlarmController srv = createService();
		srv.findById(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testFindById2() {
		final AlarmController srv = createService();
		final Alarm alarm = new Alarm();
		when(alarmService.findById(any())).thenReturn(Optional.of(alarm));
		srv.findById(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testListAlarm() {
		final AlarmController srv = createService();
		srv.listAlarm();
		assertTrue(true);
	}
}
