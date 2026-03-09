package playground.apps.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.model.dto.MenuNodeDTO;
import playground.model.entity.plain.Menu;
import playground.model.repository.MenuRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuSvc {
    private final MenuRepository repo;

    @Transactional(readOnly = true)
    public List<MenuNodeDTO> getMenuTree() {
        List<Menu> rows = repo.findByUseYnTrueOrderByMenuLvAscPrntNoAscMenuNoAsc();

        // Menu 엔티티 → Node 매핑
        Map<Long, MenuNodeDTO> byId = new LinkedHashMap<>();
        for (Menu m : rows) {
            byId.put(m.getMenuNo(), MenuNodeDTO.of(m));
        }

        List<MenuNodeDTO> roots = new ArrayList<>();

        for (Menu m : rows) {
            // “기본(루트)” 레벨은 표시하지 않음
            if (m.getMenuLv() == 0) continue;

            MenuNodeDTO node = byId.get(m.getMenuNo());
            Long parentId = m.getPrntNo();

            // “기본(1)”은 제외하고, 그 밑의 자식들(menu.prntNo == 1)은 루트가 아님!
            if (parentId == null || parentId == 0) {
                // 부모가 아예 없는 경우 (예: 독립형 Home)
                roots.add(node);
            } else if (parentId == 1L) {
                // 부모가 기본(1)이면, 기본의 “자식이 루트 후보”
                roots.add(node);
            } else {
                // 나머지는 전부 parent 밑으로 연결
                MenuNodeDTO parent = byId.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(node);
                } else {
                    // 혹시 부모가 빠진 경우 fallback
                    roots.add(node);
                }
            }
        }

        return roots;
    }


}
