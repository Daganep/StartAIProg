package com.openkin.startaiprog.domain.mapper

import com.openkin.startaiprog.data.model.MessageDbo
import com.openkin.startaiprog.domain.model.ChatMessageUI
import com.openkin.startaiprog.domain.model.MessageDto
import com.openkin.startaiprog.domain.model.MessageType

fun messageDtoToDbo(dto: MessageDto): MessageDbo =
    MessageDbo(
        messageId = dto.messageId,
        chatId = dto.chatId,
        message = dto.message,
        timestamp = dto.timestamp,
        outgoing = dto.outgoing,
        tokensCount = dto.tokensCount,
        type = dto.type.name.lowercase(),
    )

fun messageDboToDto(dbo: MessageDbo): MessageDto =
    MessageDto(
        messageId = dbo.messageId,
        chatId = dbo.chatId,
        message = dbo.message,
        timestamp = dbo.timestamp,
        outgoing = dbo.outgoing,
        tokensCount = dbo.tokensCount,
        type = MessageType.entries.first { it.name.lowercase() == dbo.type },
    )

fun messageDtoToUi(dto: MessageDto): ChatMessageUI =
    ChatMessageUI(
        messageId = dto.messageId,
        message = dto.message,
        timestamp = dto.timestamp,
        outgoing = dto.outgoing,
        tokensCount = dto.tokensCount,
        type = dto.type,
    )
