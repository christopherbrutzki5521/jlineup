package de.otto.jlineup.config;

import com.google.common.collect.ImmutableList;
import de.otto.jlineup.browser.BrowserTest;
import org.junit.Test;

import java.io.FileNotFoundException;

import static de.otto.jlineup.browser.Browser.Type.CHROME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfigTest {

    @Test
    public void shouldReadConfig() throws FileNotFoundException {
        Config config = Config.readConfig("src/test/resources/", "lineup_test.json");
        assertThatConfigContentsAreCorrect(config);
    }

    @Test
    public void shouldReadConfigFromDifferentPathThanWorkingDir() throws FileNotFoundException {
        Config config = Config.readConfig("someWorkingDir", "src/test/resources/lineup_test.json");
        assertThatConfigContentsAreCorrect(config);
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowFileNotFoundExceptionWhenConfigFileIsNotFound() throws FileNotFoundException {
        Config.readConfig("someWorkingDir", "nonexisting.json");
        //assert that filenotfoundexception is thrown (see expected above)
    }

    private void assertThatConfigContentsAreCorrect(Config config) {
        assertThat(config.getBrowser(), is(CHROME));
        assertThat(config.getAsyncWait(), is(2f));
        assertThat(config.getUrls().get("https://www.otto.de").windowWidths, is(ImmutableList.of(600, 800, 1200)));
        assertThat(config.getUrls().get("https://www.otto.de").paths, is(ImmutableList.of("/","multimedia")));
        assertThat(config.getUrls().get("https://www.otto.de"), is(BrowserTest.getExpectedUrlConfigForOttoDe()));
        assertThat(config.getUrls().get("http://www.google.de"), is(BrowserTest.getExpectedUrlConfigForGoogleDe()));
    }

}