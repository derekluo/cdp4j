/**
 * The MIT License
 * Copyright © 2017 WebFolder OÜ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.webfolder.cdp.sample;

import java.util.ArrayList;
import java.util.List;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class IncognitoBrowsing {

    // Requires Headless Chrome
    // https://chromium.googlesource.com/chromium/src/+/lkgr/headless/README.md
    public static void main(String[] args) {
        List<String> arguments = new ArrayList<String>();
        arguments.add("--headless");
        arguments.add("--disable-gpu");
        SessionFactory factory = new Launcher().launch(arguments);

        String  firstContext = factory.createBrowserContext();
        Session firstSession = factory.create(firstContext);

        firstSession.navigate("https://httpbin.org/cookies/set?SESSION_ID=1");
        firstSession.wait(500);
        String session1 = (String) firstSession.evaluate("window.document.body.textContent");
        factory.disposeBrowserContext(firstContext);

        System.out.println(session1);

        String  secondContext = factory.createBrowserContext();
        Session secondSession = factory.create(secondContext);

        secondSession.navigate("https://httpbin.org/cookies");
        firstSession.wait(500);
        String session2 = (String) secondSession.evaluate("window.document.body.textContent");
        factory.disposeBrowserContext(secondContext);

        System.out.println(session2);


        factory.close();
    }
}
