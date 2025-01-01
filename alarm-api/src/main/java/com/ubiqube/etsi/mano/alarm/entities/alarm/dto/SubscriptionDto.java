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
package com.ubiqube.etsi.mano.alarm.entities.alarm.dto;

import java.net.URI;

import com.ubiqube.etsi.mano.alarm.entities.AuthentificationInformations;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Getter
@Setter
public class SubscriptionDto {
	@Schema(description = "Authentication informations.")
	@NotNull
	private AuthentificationInformations authentication;

	@Schema(description = "Callback URL when alarm is triggered.")
	@NotNull
	private URI callbackUri;

	@Schema(description = "External reference, used for tracking alarm triggering.")
	private String remoteId;

}
