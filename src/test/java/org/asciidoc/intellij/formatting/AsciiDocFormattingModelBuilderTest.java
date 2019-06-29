package org.asciidoc.intellij.formatting;

import com.intellij.codeInsight.actions.FileInEditorProcessor;
import com.intellij.codeInsight.actions.LayoutCodeOptions;
import com.intellij.codeInsight.actions.ReformatCodeRunOptions;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import org.asciidoc.intellij.editor.javafx.JavaFxHtmlPanelProvider;
import org.asciidoc.intellij.settings.AsciiDocApplicationSettings;
import org.asciidoc.intellij.settings.AsciiDocPreviewSettings;

import java.io.File;

import static com.intellij.codeInsight.actions.TextRangeType.WHOLE_FILE;

public class AsciiDocFormattingModelBuilderTest extends LightPlatformCodeInsightFixtureTestCase {

  @Override
  protected String getTestDataPath() {
    return new File("testData/" + getBasePath()).getAbsolutePath() + "/actions/reformatFileInEditor/";
  }

  private void doTest(LayoutCodeOptions options) {
    AsciiDocPreviewSettings oldPreviewSettings = AsciiDocApplicationSettings.getInstance().getAsciiDocPreviewSettings();

    AsciiDocApplicationSettings.getInstance().setAsciiDocPreviewSettings(
      new AsciiDocPreviewSettings(
        oldPreviewSettings.getSplitEditorLayout(),
        new JavaFxHtmlPanelProvider().getProviderInfo(),
        oldPreviewSettings.getPreviewTheme(),
        oldPreviewSettings.getAttributes(),
        oldPreviewSettings.isVerticalSplit(),
        oldPreviewSettings.isEditorFirst(),
        oldPreviewSettings.isEnabledInjections(),
        oldPreviewSettings.getDisabledInjectionsByLanguage(),
        true)
    );
    myFixture.configureByFile(getTestName(true) + "_before.adoc");
    FileInEditorProcessor processor = new FileInEditorProcessor(myFixture.getFile(), myFixture.getEditor(), options);
    processor.processCode();
    myFixture.checkResultByFile(getTestName(true) + "_after.adoc");
  }

  public void testHeadings() {
    doTest(new ReformatCodeRunOptions(WHOLE_FILE));
  }

  public void testEnumerations() {
    doTest(new ReformatCodeRunOptions(WHOLE_FILE));
  }

  public void testBlocks() {
    doTest(new ReformatCodeRunOptions(WHOLE_FILE));
  }

}
