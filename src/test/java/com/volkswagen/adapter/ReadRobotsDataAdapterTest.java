package com.volkswagen.adapter;

import com.volkswagen.adapter.file.in.ReadRobotsDataAdapter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadRobotsDataAdapterTest {

    private static final String DEFAULT_FILE_PATH = "robots.txt";

    @Test
    void creates_a_read_robots_data_adapter() {
        ReadRobotsDataAdapter readRobotsDataAdapter = new ReadRobotsDataAdapter();
        assertThat(readRobotsDataAdapter.readDataFrom(DEFAULT_FILE_PATH)).isNotEmpty();
    }

    @Test
    void creates_a_read_robots_data_adapter_with_null_file_path() {
        ReadRobotsDataAdapter readRobotsDataAdapter = new ReadRobotsDataAdapter();
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> readRobotsDataAdapter.readDataFrom(null));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("File path cannot be null");
    }

    @Test
    void creates_a_read_robots_data_adapter_with_invalid_file_path() {
        ReadRobotsDataAdapter readRobotsDataAdapter = new ReadRobotsDataAdapter();
        assertThat(readRobotsDataAdapter.readDataFrom("invalid.txt")).isEmpty();
    }

}
