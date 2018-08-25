package com.gkshanmugavel.newapp;

import com.gkshanmugavel.newapp.model.TitleModel;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class TitleListTest {
    @Test
    public void test_array_pass() {
        List<TitleModel> actual = Arrays.asList(new TitleModel("title", "description", "image"));
        List<TitleModel> expected = Arrays.asList(new TitleModel("title", "description", "image"));
        assertThat(actual, is(expected));
        assertThat(actual, is(not(expected)));
    }
}
