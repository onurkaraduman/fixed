import com.noxml.reader.XmlReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.nio.file.Paths;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
public class Test {

    @org.junit.Test
    public void test() throws DocumentException {
        XmlReader xmlReader = new XmlReader(Paths.get("src/main/resources/com/noxml/tree/fix44-short.xml"));
        Document document = xmlReader.getDocument();
        Element rootElement = document.getRootElement();
        Element testElement = DocumentHelper.createElement("testElement");
        rootElement.add(testElement);
    }
}
