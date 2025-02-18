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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.alarm.entities.AlarmElement;
import com.ubiqube.etsi.mano.alarm.entities.AlarmSubscription;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.AlarmDto;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.SubscriptionDto;
import com.ubiqube.etsi.mano.alarm.service.AlarmService;
import com.ubiqube.etsi.mano.alarm.service.mapper.AggregatesMapper;
import com.ubiqube.etsi.mano.alarm.service.mapper.AuthenticationMapper;
import com.ubiqube.etsi.mano.alarm.service.mapper.MetricMapper;
import com.ubiqube.etsi.mano.alarm.service.mapper.TransformMapper;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

/**
 *
 * @author Olivier Vignaud
 *
 */
@RestController
@RequestMapping("/")
@Validated
public class AlarmController {
	private final AlarmService alarmService;
	private final TransformMapper transformMapper;
	private final MetricMapper metricMapper;
	private final AuthenticationMapper authenticationMapper;
	private final AggregatesMapper aggregatesMapper;

	public AlarmController(final AlarmService alarmService, final TransformMapper transformMapper, final MetricMapper metricMapper, final AuthenticationMapper authenticationMapper, final AggregatesMapper aggregatesMapper) {
		this.alarmService = alarmService;
		this.transformMapper = transformMapper;
		this.metricMapper = metricMapper;
		this.authenticationMapper = authenticationMapper;
		this.aggregatesMapper = aggregatesMapper;
	}

	@Operation(summary = "List all Alarms", description = "List all alarms.", tags = {})
	@GetMapping
	public ResponseEntity<List<Alarm>> listAlarm() {
		final List<Alarm> lst = alarmService.find();
		return ResponseEntity.ok(lst);
	}

	@Operation(summary = "Get a unique alarm.", description = "Get a unique alarm.", tags = {})
	@GetMapping("/{id}")
	public ResponseEntity<Alarm> findById(final @NonNull @PathVariable("id") UUID id) {
		final Optional<Alarm> res = alarmService.findById(id);
		if (res.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(res.get());
	}

	@Operation(summary = "Create an alarm.", description = "Create an alarm.", tags = {})
	@PostMapping
	public ResponseEntity<Alarm> createAlarm(final @RequestBody @Valid AlarmDto alarm) {
		final Alarm subs = map(alarm);
		final Alarm res = alarmService.create(subs);
		return ResponseEntity.ok(res);
	}

	@Operation(summary = "Delete an alarm.", description = "Delete an alarm.", tags = {})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAlaram(final @NonNull @PathVariable("id") UUID id) {
		alarmService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Add an element into an existing alarm.", description = "Add element alarm.", tags = {})
	@PostMapping("/{id}/element")
	public ResponseEntity<Void> addElementAlaram(final @NonNull @PathVariable("id") UUID id, @NonNull @RequestBody final AlarmElement alarmElement) {
		alarmService.addElement(id, alarmElement);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Remove an element into an existing alarm.", description = "Remove element alarm.", tags = {})
	@DeleteMapping("/{id}/element/{name}")
	public ResponseEntity<Void> deleteElementAlaram(final @NonNull @PathVariable("id") UUID id, final @NonNull @PathVariable("name") String name) {
		alarmService.deleteElement(id, name);
		return ResponseEntity.noContent().build();
	}

	@NonNull
	private Alarm map(final AlarmDto alarm) {
		final Alarm a = new Alarm();
		a.setAggregates(aggregatesMapper.map(alarm.getAggregates()));
		a.setConditions(alarm.getConditions());
		a.setMetrics(metricMapper.map(alarm.getMetrics()));
		a.setTransforms(transformMapper.map(alarm.getTransforms()));
		a.setSubscription(map(alarm.getSubscription()));
		return a;
	}

	private AlarmSubscription map(final SubscriptionDto subscription) {
		final AlarmSubscription s = new AlarmSubscription();
		s.setAuthentication(authenticationMapper.map(subscription.getAuthentication()));
		s.setCallbackUri(subscription.getCallbackUri());
		s.setRemoteId(subscription.getRemoteId());
		return s;
	}
}
