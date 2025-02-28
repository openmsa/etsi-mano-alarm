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
package com.ubiqube.etsi.mano.alarm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.AlarmException;
import com.ubiqube.etsi.mano.alarm.entities.AlarmElement;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Aggregates;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Metrics;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Transform;
import com.ubiqube.etsi.mano.alarm.repository.AlarmRepository;
import com.ubiqube.etsi.mano.alarm.service.aggregate.AggregateService;
import com.ubiqube.etsi.mano.alarm.service.mapper.MetricMapper;
import com.ubiqube.etsi.mano.alarm.service.mapper.TransformMapper;
import com.ubiqube.etsi.mano.alarm.service.transform.TransformService;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class AlarmService {
	private final AlarmRepository alarmRepository;
	private final TransformService transformService;
	private final AggregateService aggregateService;
	private final MetricMapper metricMapper;
	private final TransformMapper transformMapper;

	public AlarmService(final AlarmRepository alarmRepository, final TransformService transformService, final AggregateService aggregateService, final MetricMapper metricMapper, final TransformMapper transformMapper) {
		this.alarmRepository = alarmRepository;
		this.transformService = transformService;
		this.aggregateService = aggregateService;
		this.metricMapper = metricMapper;
		this.transformMapper = transformMapper;
	}

	public List<Alarm> find() {
		final Iterable<Alarm> ite = alarmRepository.findAll();
		return StreamSupport.stream(ite.spliterator(), false).toList();
	}

	public Alarm create(final @NonNull Alarm subs) {
		checkTransforms(subs.getTransforms());
		checkAggregates(subs.getAggregates());
		return alarmRepository.save(subs);
	}

	public void deleteById(final @NonNull UUID id) {
		alarmRepository.deleteById(id);
	}

	public Optional<Alarm> findById(final @NonNull UUID id) {
		return alarmRepository.findById(id);
	}

	private void checkAggregates(final List<Aggregates> aggregates) {
		final List<String> errors = aggregateService.checkErrors(aggregates);
		if (!errors.isEmpty()) {
			throw new AlarmException("Following aggregates are not defined: " + errors);
		}
	}

	private void checkTransforms(final List<Transform> list) {
		final List<String> errors = transformService.checkErrors(list);
		if (!errors.isEmpty()) {
			throw new AlarmException("Following functions are not defined: " + errors);
		}
	}

	public void addElement(final @NonNull UUID id, final AlarmElement alarmElement) {
		final Alarm alarm = alarmRepository.findById(id).orElseThrow();
		if (alarm.getMetrics().stream().anyMatch(x -> x.getLabel().equals(alarmElement.getMetric().getLabel()))) {
			throw new AlarmException("Element already exist.");
		}
		alarm.getMetrics().add(metricMapper.map(alarmElement.getMetric()));
		List<Transform> tmp = alarmElement.getTransforms().stream().map(transformMapper::map).toList();
		alarm.getTransforms().addAll(tmp);
		alarmRepository.save(alarm);
	}

	public void deleteElement(final @NonNull UUID id, final String name) {
		final Alarm alarm = alarmRepository.findById(id).orElseThrow();
		final List<Metrics> m = alarm.getMetrics().stream().filter(x -> x.getLabel().equals(name)).toList();
		final List<Aggregates> a = alarm.getAggregates().stream().filter(x -> x.getName().equals(name)).toList();
		alarm.setMetrics(m);
		alarm.setAggregates(a);
		alarmRepository.save(alarm);
	}

}
