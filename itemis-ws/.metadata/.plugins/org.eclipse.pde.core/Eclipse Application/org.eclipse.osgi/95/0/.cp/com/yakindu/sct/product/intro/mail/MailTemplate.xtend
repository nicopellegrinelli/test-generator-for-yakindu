/**
 * Copyright (c) 2022 itemis AG - All rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * 
 * Contributors:
 * 	Andreas Muelder - itemis AG
 * 
 */
package com.yakindu.sct.product.intro.mail

import java.net.URLEncoder
import java.io.UnsupportedEncodingException

class MailTemplate {

	def String content() {
		'''
			<< 
			   Please enter your request here.
			   After sending the request you will automatically receive a confirmation with a ticket number.
			>>
			
			itemis CREATE («PluginVersion.version»)
		'''
	}

	def String sctProVersion() {
		'''«PluginVersion.version»'''
	}

	def String subject() {
		'''itemis CREATE «PluginVersion.simpleVersion» Support Request
		'''
	}

	def String receiver() {
		'''sct-pro@itemis.de'''
	}

	def String asMailTo() {
		'''mailto:«enc(receiver())»?subject=«enc(subject())»&body=«enc(content())»'''
	}

	def String asMailToForLinuxWithGtk() {
		'''mailto:?to=«enc(receiver())»&subject=«enc(subject())»&body=«enc(content())»'''
	}

	def String enc(String p) {
		if (p === null)
			return "";
		try {
			return URLEncoder.encode(p, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException();
		}
	}

}
