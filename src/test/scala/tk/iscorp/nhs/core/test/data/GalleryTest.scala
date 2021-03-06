/*******************************************************************************
 Copyright 2018 bodand

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ******************************************************************************/
package tk.iscorp.nhs.core.test.data

import org.apache.commons.io.FileUtils
import org.junit.Assert._
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import tk.iscorp.nhs.core.data.Gallery
import tk.iscorp.nhs.core.data.hentai._

import java.io.File

@RunWith(classOf[JUnitRunner])
class GalleryTest extends WordSpec {
  private var gallery1: Gallery = _
  private val gallery2eq1: Gallery =
    new Gallery(
      "(C71) [Arisan-Antenna (Koari)] Eat The Rich! (Sukatto Golf Pangya)",
      "(C71) [ありさんアンテナ (小蟻)] Eat The Rich! (スカッとゴルフ パンヤ)",
      Array(new HentaiParody("pangya", 78)),
      Array(new HentaiCharacter("kooh", 41)),
      Array(
        new HentaiTag("lolicon", 45693),
        new HentaiTag("catgirl", 5796),
        new HentaiTag("gymshorts", 176)
      ),
      Array(new HentaiArtist("koari", 46)),
      Array(new HentaiGroup("arisan-antenna", 34)),
      Array(new JapaneseHentai(129652)),
      new DoujinshiHentai(138514),
      14,
      "June 28, 2014, 2:12 p.m.",
      1,
      9
    )
  private val gallery3ne1: Gallery = Gallery.dummy()
  "A gallery" when {
    "created" should {
      "initialize" in {
        gallery1 = new Gallery(
          "(C71) [Arisan-Antenna (Koari)] Eat The Rich! (Sukatto Golf Pangya)",
          "(C71) [ありさんアンテナ (小蟻)] Eat The Rich! (スカッとゴルフ パンヤ)",
          Array(new HentaiParody("pangya", 78)),
          Array(new HentaiCharacter("kooh", 41)),
          Array(
            new HentaiTag("lolicon", 45693),
            new HentaiTag("catgirl", 5796),
            new HentaiTag("gymshorts", 176)
          ),
          Array(new HentaiArtist("koari", 46)),
          Array(new HentaiGroup("arisan-antenna", 34)),
          Array(new JapaneseHentai(129652)),
          new DoujinshiHentai(138514),
          14,
          "June 28, 2014, 2:12 p.m.",
          1,
          9
        )
        assertNotNull(gallery1)
      }
    }
    "equality checked" should {
      "true" when {
        "ids match" in {
          assertTrue(gallery1 == gallery2eq1)
        }
      }
      "false" when {
        "ids don't match" in {
          assertFalse(gallery1 == gallery3ne1)
        }
        "other isn't a gallery" in {
          val obj = new File("Your_life_is_meaningless_and_so_is_the_universe.it_is_the_truth")
          //noinspection ComparingUnrelatedTypes
          assertFalse(gallery1 == obj)
        }
      }
    }
    "xml requested" should {
      "return valid xml" in {
        val xmlString =
          <gallery id="1" data-id="9">
  <name>(C71) [Arisan-Antenna (Koari)] Eat The Rich! (Sukatto Golf Pangya)</name>
  <sec-name>(C71) [ありさんアンテナ (小蟻)] Eat The Rich! (スカッとゴルフ パンヤ)</sec-name>
  <parodies>
    <parody name="pangya" amount="78"/>
  </parodies>
  <characters>
    <character name="kooh" amount="41"/>
  </characters>
  <tags>
    <tag name="lolicon" amount="45693" /><tag name="catgirl" amount="5796" /><tag name="gymshorts" amount="176" />
  </tags>
  <artists>
    <artist name="koari" amount="46" />
  </artists>
  <groups>
    <group name="arisan-antenna" amount="34" />
  </groups>
  <languages>
    <language name="Japanese" amount="129652" />
  </languages>
  <category name="Doujinshi" amount="138514" />
  <pages size="14" />
  <upload>June 28, 2014, 2:12 p.m.</upload>
</gallery>.toString() // fuck this shit
        assertEquals(xmlString, gallery1.toXml.toString())
      }
    }
    "json requested" should {
      "return valid json" in {
        val jsonString =
          """{"languages":[{"language":{"amount":129652,"name":"Japanese"}}],
            |"upload":"June 28, 2014, 2:12 p.m.",
            |"groups":[{"group":{"amount":34,"name":"arisan-antenna"}}],
            |"tags":[{"tag":{"amount":45693,"name":"lolicon"}},
            |{"tag":{"amount":5796,"name":"catgirl"}},
            |{"tag":{"amount":176,"name":"gymshorts"}}],
            |"characters":[{"character":{"amount":41,"name":"kooh"}}],
            |"parodies":[{"parody":{"amount":78,"name":"pangya"}}],
            |"pages":14,"artists":[{"artist":{"amount":46,"name":"koari"}}],
            |"name":"(C71) [Arisan-Antenna (Koari)] Eat The Rich! (Sukatto Golf Pangya)",
            |"data-id":9,
            |"id":1,
            |"category":{"name":"Doujinshi","amount":138514},
            |"sec-name":"(C71) [ありさんアンテナ (小蟻)] Eat The Rich! (スカッとゴルフ パンヤ)"}""".stripMargin
            .replaceAll("(?<=,)\\s+(?!\\d)", "")

        val gotString = gallery1.toJson.toJSONString

        FileUtils.writeStringToFile(new File("exp.bodandkeep.txt"), jsonString, "utf-8")
        FileUtils.writeStringToFile(new File("got.bodandkeep.txt"), gotString, "utf-8")

        assertEquals(jsonString, gotString)
      }
    }
  }
}
