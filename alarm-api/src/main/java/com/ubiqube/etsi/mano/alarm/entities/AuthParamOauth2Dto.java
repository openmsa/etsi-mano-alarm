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
package com.ubiqube.etsi.mano.alarm.entities;

import java.net.URI;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthParamOauth2Dto {
	private String clientId;
	private String clientSecret;
	@NotNull
	private URI tokenEndpoint;
	private String o2Username;
	private String o2Password;
	private Boolean o2IgnoreSsl;
	@NotNull
	private OAuth2GrantTypeDto grantType;
	private String o2AuthTlsCert;
}
