//regras de negocio

package academy.devdojo.springboot2.service;
import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private  final AnimeRepository animeRepository;
    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById(id) .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody ) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void remove(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime salvedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime =  AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(salvedAnime.getId());
        animeRepository.save(anime);
    }
}
