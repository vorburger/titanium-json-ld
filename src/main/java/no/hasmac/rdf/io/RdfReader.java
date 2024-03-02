/*
 * Copyright 2020 APICATALOG and HASMAC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.hasmac.rdf.io;

import no.hasmac.rdf.RdfDataset;
import no.hasmac.rdf.io.error.RdfReaderException;

import java.io.IOException;

public interface RdfReader {

    RdfDataset readDataset() throws IOException, RdfReaderException;

}
