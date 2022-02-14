package com.rokt.helpers;

import com.google.inject.Inject;
import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseParser {
    @Inject
    private DateTimeHelper dateTimeHelper;

    public SearchResponse convertToSearchResponse(Record a) {
        String recordDateTime = dateTimeHelper.convert(a.getDateTime());
        return new SearchResponse(recordDateTime,
                a.getEmail(),
                a.getSessionId());
    }
}
