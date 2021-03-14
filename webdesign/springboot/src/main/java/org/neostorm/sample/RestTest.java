package org.neostorm.sample;

/*
   Copyright 2018 Wall Street Fin Tech

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. 
   
    
    */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.neostorm.elemental_ui.uiBuilder;
import org.springframework.web.bind.annotation.*;


@RestController
public class RestTest {

    int cnt = 1;

    @RequestMapping("/hw")
    public String greeting() {
        String json = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();
            node.put("msg", String.format("Hello World %d", cnt++));
            json = mapper.writeValueAsString(node);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }

    @RequestMapping("/data")
    public String getData() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("l1", "Kevin");
        node.put("l2", "Boyle");
        String json = null;

        try {
            json = mapper.writeValueAsString(node);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }

    @RequestMapping("/layout")
    public String getLayout() {

//        uiBuilder[] pages = getElaborate();
//        uiBuilder[] pages = getComponents();

        uiBuilder[] pages = getTwoColumnForm();

        String json = uiBuilder.toJson(pages);
        return json;
    }

    private uiBuilder[] getTwoColumnForm() {
        uiBuilder[] pages = {
            uiBuilder.div().attr("class", "container py-5").children(
                    uiBuilder.div().attr("class", "row").children(
                            uiBuilder.div().attr("class", "col-md-10 mx-auto").children(
                                    uiBuilder.form().children(
                                            uiBuilder.div().attr("class", "form-group row").children(
                                                    uiBuilder.div().attr("class", "col-sm-6").children(
                                                            uiBuilder.label().attr("for", "inputFirstname").content("First name"),
                                                            uiBuilder.text_input().attr("type","text").attr("class", "form-control").attr("id", "inputFirstname").attr("placeholder", "First Name")
                                                            ),
                                                    uiBuilder.div().attr("class", "col-sm-6").children (
                                                            uiBuilder.label().attr("for", "inputLastname").content("Last name"),
                                                            uiBuilder.text_input().attr("type", "text").attr("class", "form-control").attr("id", "inputLastname").attr("placeholder","Last Name")
                                                    )
                                            ),
                                            uiBuilder.div().attr("class", "form-group row").children(
                                                    uiBuilder.div().attr("class", "col-sm-6").children(
                                                            uiBuilder.label().attr("for", "inputFirstname").content("First name"),
                                                            uiBuilder.text_input().type("text").attr("class", "form-control").attr("id", "inputFirstname2").attr("placeholder","First Name")
                                                    ),
                                                    uiBuilder.div().attr("class", "col-sm-6").children (
                                                            uiBuilder.label().attr("for","inputLastname").content("Last name"),
                                                            uiBuilder.text_input().type("text").attr("class", "form-control").attr("id", "inputLastname2").attr("placeholder","Last Name")
                                                    )
                                            )
                                    )
                            )
                    )
            )
        };

        return pages;
    }

    private uiBuilder[] getHtmlExample() {
        uiBuilder[] pages = {
                uiBuilder.body().data_target("#main-nav").id("home").children(
                        uiBuilder.nav().clz("navbar navbar-expand-sm bg-dark navbar-dark fixed-top").id("main-nav").children(
                                uiBuilder.div().clz("container").children(
                                        uiBuilder.a().href("index.html").clz("navbar-brand").content("UI Api Example"),
                                        uiBuilder.button().clz("navbar-toggler").data_toggle("collapse").data_target("#navbarCollapse").children(
                                                uiBuilder.span().clz("navbar-toggler-icon")
                                        ),
                                        uiBuilder.div().clz("collapse navbar-collapse").id("navbarCollapse").children(
                                                uiBuilder.ul().clz("navbar-nav ml-auto").children(
                                                        uiBuilder.li().clz("nav-item").children(
                                                                uiBuilder.a().href("#home").clz("nav-link").content("Home")
                                                        ),
                                                        uiBuilder.li().clz("nav-item").children(
                                                                uiBuilder.a().href("#explore-head-section").clz("nav-link").content("Explore")
                                                        ),
                                                        uiBuilder.li().clz("nav-item").children(
                                                                uiBuilder.a().href("#create-head-section").clz("nav-link").content("Create")
                                                        ),
                                                        uiBuilder.li().clz("nav-item").children(
                                                                uiBuilder.a().href("#share-head-section").clz("nav-link").content("Share")
                                                        )
                                                )
                                        )
                                )
                        ),
                        uiBuilder.header().id("home-section").children(
                                uiBuilder.div().clz("dark-overlay").children(
                                        uiBuilder.div().clz("home-inner container").children(
                                                uiBuilder.div().clz("row").children(
                                                        uiBuilder.div().clz("col-lg-8 d-none d-lg-block").children(
                                                                uiBuilder.h1().clz("display-4").content("Build ").children(
                                                                        uiBuilder.strong().content("Social profiles"),
                                                                        uiBuilder.div().content("and gain revenue "),
                                                                        uiBuilder.strong().content("profits")
                                                                ),
                                                                uiBuilder.div().clz("d-flex").children(
                                                                        uiBuilder.div().clz("p-4 align-self-start").children(
                                                                                uiBuilder.i().clz("fas fa-check fa-2x")
                                                                        ),
                                                                        uiBuilder.div().clz("p-4 align-self-end").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Dignissimos odit molestias corporis incidunt provident fuga!")
                                                                ),
                                                                uiBuilder.div().clz("d-flex").children(
                                                                        uiBuilder.div().clz("p-4 align-self-start").children(
                                                                                uiBuilder.i().clz("fas fa-check fa-2x")
                                                                        ),
                                                                        uiBuilder.div().clz("p-4 align-self-end").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Dignissimos odit molestias corporis incidunt provident fuga!")
                                                                ),
                                                                uiBuilder.div().clz("d-flex").children(
                                                                        uiBuilder.div().clz("p-4 align-self-start").children(
                                                                                uiBuilder.i().clz("fas fa-check fa-2x")
                                                                        ),
                                                                        uiBuilder.div().clz("p-4 align-self-end").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Dignissimos odit molestias corporis incidunt provident fuga!")
                                                                )
                                                        ),
                                                        uiBuilder.div().clz("col-lg-4").children(
                                                                uiBuilder.div().clz("card bg-primary text-center card-form").children(
                                                                        uiBuilder.div().clz("card-body").children(
                                                                                uiBuilder.h3().content("Sign Up Today"),
                                                                                uiBuilder.p().content("Please fill out this form to register"),
                                                                                uiBuilder.form().children(
                                                                                        uiBuilder.div().clz("form-group").children(
                                                                                                uiBuilder.text_input().type("text").clz("form-control form-control-lg").placeholder("Username")
                                                                                        ),
                                                                                        uiBuilder.div().clz("form-group").children(
                                                                                                uiBuilder.text_input().type("email").clz("form-control form-control-lg").placeholder("Email")
                                                                                        ),
                                                                                        uiBuilder.div().clz("form-group").children(
                                                                                                uiBuilder.text_input().type("password").clz("form-control form-control-lg").placeholder("Password")
                                                                                        ),
                                                                                        uiBuilder.div().clz("form-group").children(
                                                                                                uiBuilder.text_input().type("password").clz("form-control form-control-lg").placeholder("Confirm Password")
                                                                                        ),
                                                                                        uiBuilder.text_input().type("submit").value("Submit").clz("btn btn-outline-light btn-block")
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        ),
                        uiBuilder.section().id("explore-head-section").children(
                                uiBuilder.div().clz("container").children(
                                        uiBuilder.div().clz("row").children(
                                                uiBuilder.div().clz("col text-center py-5").children(
                                                        uiBuilder.h1().clz("display-4").content("Explore"),
                                                        uiBuilder.p().clz("lead").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Nam vel temporibus maiores doloremque quam vero pariatur quasi vitae laboriosam veritatis."),
                                                        uiBuilder.a().href("").clz("btn btn-outline-secondary").content("Find Out More")
                                                )
                                        )
                                )
                        ),
                        uiBuilder.section().id("explore-section").clz("bg-light text-muted py-5").children(
                                uiBuilder.div().clz("container").children(
                                        uiBuilder.div().clz("row").children(
                                                uiBuilder.div().clz("col-md-6").children(
                                                        uiBuilder.img().src("img/explore-section1.jpg").alt("").clz("img-fluid mb-3 rounded-circle")
                                                ),
                                                uiBuilder.div().clz("col-md-6").children(
                                                        uiBuilder.h3().content("Explore & Connect"),
                                                        uiBuilder.p().content("orem ipsum dolor, sit amet consectetur adipisicing elit. Voluptatibus molestiae dolorum soluta quod aut alias animi ut error perferendis debitis."),
                                                        uiBuilder.div().clz("d-flex").children(
                                                                uiBuilder.div().clz("p-4 align-self-start").children(
                                                                        uiBuilder.i().clz("fas fa-check fa-2x")
                                                                ),
                                                                uiBuilder.div().clz("p-4 align-self-start").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Assumenda, in ipsam vero nobis soluta voluptas!")
                                                        ),
                                                        uiBuilder.div().clz("d-flex").children(
                                                                uiBuilder.div().clz("p-4 align-self-start").children(
                                                                        uiBuilder.i().clz("fas fa-check fa-2x")
                                                                ),
                                                                uiBuilder.div().clz("p-4 align-self-start").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Assumenda, in ipsam vero nobis soluta voluptas!")
                                                        )
                                                )
                                        )
                                )
                        ),
                        uiBuilder.section().id("create-head-section").clz("bg-primary").children(
                                uiBuilder.div().clz("container").children(
                                        uiBuilder.div().clz("row").children(
                                                uiBuilder.div().clz("col text-center py-5"),
                                                uiBuilder.h1().clz("display-4").content("Create"),
                                                uiBuilder.p().clz("lead").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Nam vel temporibus maiores doloremque quam vero pariatur quasi vitae laboriosam veritatis."),
                                                uiBuilder.a().href("").clz("btn btn-outline-secondary").content("Find Out More")
                                        )
                                )
                        ),
                        uiBuilder.section().id("create-section").clz("py-5").children(
                                uiBuilder.div().clz("container").children(
                                        uiBuilder.div().clz("row").children(
                                                uiBuilder.div().clz("col-md-6 order-2").children(
                                                        uiBuilder.img().src("img/create-section1.jpg").alt("").clz("img-fluid mb-3 rounded-circle")
                                                ),
                                                uiBuilder.div().clz("col-md-6 order-1").children(
                                                        uiBuilder.h3().content("Create Your Passion"),
                                                        uiBuilder.p().content("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Voluptatibus molestiae dolorum soluta quod aut alias animi ut error perferendis debitis."),
                                                        uiBuilder.div().clz("d-flex").children(
                                                                uiBuilder.div().clz("p-4 align-self-start").children(
                                                                        uiBuilder.i().clz("fas fa-check fa-2x")
                                                                ),
                                                                uiBuilder.div().clz("p-4 align-self-start").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Assumenda, in ipsam vero nobis soluta voluptas!")
                                                        ),
                                                        uiBuilder.div().clz("d-flex").children(
                                                                uiBuilder.div().clz("p-4 align-self-start").children(
                                                                        uiBuilder.i().clz("fas fa-check fa-2x")
                                                                ),
                                                                uiBuilder.div().clz("p-4 align-self-start").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Assumenda, in ipsam vero nobis soluta voluptas!")
                                                        )
                                                )
                                        )
                                )
                        ),
                        uiBuilder.section().id("share-head-section").clz("bg-primary").children(
                                uiBuilder.div().clz("container").children(
                                        uiBuilder.div().clz("row").children(
                                                uiBuilder.div().clz("col text-center py-5"),
                                                uiBuilder.h1().clz("display-4").content("Share"),
                                                uiBuilder.p().clz("lead").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Nam vel temporibus maiores doloremque quam vero pariatur quasi vitae laboriosam veritatis"),
                                                uiBuilder.a().href("").clz("btn btn-outline-light").content("Find Out More")
                                        )
                                )
                        ),
                        uiBuilder.section().id("share-section").clz("bg-light text-muted py-5").children(
                                uiBuilder.div().clz("container").children(
                                        uiBuilder.div().clz("row").children(
                                                uiBuilder.div().clz("col-md-6").children(
                                                        uiBuilder.img().src("img/share-section1.jpg").alt("").clz("img-fluid mb-3 rounded-circle")
                                                ),
                                                uiBuilder.div().clz("col-md-6").children(
                                                        uiBuilder.h3().content("Share what you create"),
                                                        uiBuilder.p().content("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Voluptatibus molestiae dolorum soluta quod aut alias animi ut error perferendis debitis."),
                                                        uiBuilder.div().clz("d-flex").children(
                                                                uiBuilder.div().clz("p-4 align-self-start").children(
                                                                        uiBuilder.i().clz("fas fa-check fa-2x")
                                                                ),
                                                                uiBuilder.div().clz("p-4 align-self-start").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Assumenda, in ipsam vero nobis soluta voluptas!")
                                                        ),
                                                        uiBuilder.div().clz("d-flex").children(
                                                                uiBuilder.div().clz("p-4 align-self-start").children(
                                                                        uiBuilder.i().clz("fas fa-check fa-2x")
                                                                ),
                                                                uiBuilder.div().clz("p-4 align-self-start").content("Lorem ipsum dolor sit amet consectetur, adipisicing elit. Assumenda, in ipsam vero nobis soluta voluptas!")
                                                        )

                                                )
                                        )
                                )
                        ),
                        uiBuilder.footer().id("main-footer").clz("bg-dark").children(
                                uiBuilder.div().clz("container").children(
                                        uiBuilder.div().clz("row").children(
                                                uiBuilder.div().clz("col text-center py-4").children(
                                                        uiBuilder.h3().content("LoopLAB"),
                                                        uiBuilder.p().content("Copyright").children(
                                                                uiBuilder.span().id("year")
                                                        ),
                                                        uiBuilder.button().clz("btn btn-primary").data_toggle("modal").data_target("#contactModals").content("Contract Us")
                                                )
                                        )
                                )
                        ),
                        uiBuilder.div().clz("modal fade text-dark").id("contactModals").children(
                                uiBuilder.div().clz("modal-dialog").children(
                                        uiBuilder.div().clz("modal-content").children(
                                                uiBuilder.div().clz("modal-header").children(
                                                        uiBuilder.div().clz("modal-title").content("Contact Us"),
                                                        uiBuilder.button().clz("close").data_dismiss("modal").children(
                                                                uiBuilder.span().content("x")
                                                        )
                                                ),
                                                uiBuilder.div().clz("modal-body").children(
                                                        uiBuilder.form().children(
                                                                uiBuilder.div().clz("form-group").children(
                                                                        uiBuilder.label().forAttr("name").content("Name"),
                                                                        uiBuilder.text_input().type("text").clz("form-control")
                                                                ),
                                                                uiBuilder.div().clz("form-group").children(
                                                                        uiBuilder.label().forAttr("name").content("Email"),
                                                                        uiBuilder.text_input().type("text").clz("form-control")
                                                                ),
                                                                uiBuilder.div().clz("form-group").children(
                                                                        uiBuilder.label().forAttr("name").content("Message"),
                                                                        uiBuilder.text_input().type("text").clz("form-control")
                                                                )
                                                        )
                                                ),
                                                uiBuilder.div().clz("modal-footer").children(
                                                        uiBuilder.button().clz("bnt btn-primary btn-block").content("Submit")
                                                )
                                        )
                                )
                        )
                )
        };

        return pages;
    }

    private uiBuilder[] getComponents() {
        uiBuilder[] pages = {
                uiBuilder.button().clz("btn btn-primary").content("Press Me").click_url("/rest/post_url"),
                uiBuilder.div().clz("container").children(
                        uiBuilder.div().clz("row").children(
                                uiBuilder.div().clz("col-md-12 order-2").children(
                                        uiBuilder.h1().content("Hello World")
                                )
                        )
                ),
                uiBuilder.h2().content("H2 Hello"),
                uiBuilder.h3().content("H3 hello")
        };

        return pages;
    }

    @Test
    public void testHtml() {
        uiBuilder[] pages = getHtmlExample();

        String html = uiBuilder.toHtml(pages);


        Document doc = Jsoup.parse(html);   // pretty print HTML
        System.out.println(doc.toString());

    }

    private uiBuilder[] getElaborate() {
        uiBuilder doc = new uiBuilder();
        uiBuilder page = doc.nav().clz("navbar navbar-expand-sm bg-dark navbar-dark fixed-top").id("main-nav").children(
                doc.div().clz("container").children(
                        doc.a().clz("navbar-brand").content("LoopLAB"),
                        doc.button().clz("navbar-toggler").children(
                                doc.span().clz("navbar-toggler-icon")
                        ),
                        doc.div().clz("collapse navbar-collapse").id("navbarCollapse").children(
                                doc.ul().clz("navbar-nav ml-auto").children(
                                        doc.li().clz("nav-item").children(
                                                doc.a().href("#home").clz("nav-link").content("Home")
                                        ),
                                        doc.li().clz("nav-item").children(
                                                doc.a().href("#explore").clz("nav-link").content("Explore")
                                        ),
                                        doc.li().clz("nav-item").children(
                                                doc.a().href("#create").clz("nav-link").content("Create")
                                        ),
                                        doc.li().clz("nav-item").children(
                                                doc.a().href("#share").clz("nav-link").content("Share")
                                        )
                                )
                        )
                )
        );

        uiBuilder[] pages = {
                page
        };

        return pages;
    }

    @PostMapping("/post_url")
    public String post_url(@RequestBody String json) {
        int i = 0;
        return json;
    }


    //https://spring.io/guides/tutorials/rest/
    @PostMapping("/post")
    public String postHandler(@RequestBody String json) {
        try {
            if (json != null) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(json);
                if (node != null && node.isObject()) {
                    ObjectNode on = (ObjectNode) node;
                    if (on != null) {
                        ObjectNode sub = (ObjectNode) on.get("data").get("sub");
                        sub.put("test", "Kevin");
                        System.out.println("postHandler");
                        mapper.writerWithDefaultPrettyPrinter();
                        json = mapper.writeValueAsString(node);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }


    @Test
    public void test1() {
        uiBuilder b = new uiBuilder();

        uiBuilder[] page = getElaborate();

        String json = b.toJson(page);

        System.out.println(json);
    }

}
