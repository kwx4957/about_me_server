package com.aboutme.springwebservice.mypage.domain;

import com.aboutme.springwebservice.mypage.model.UserLevelResponseVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserLevelRepository extends JpaRepository<UserLevel, Long> {
    @Query(value="SELECT user_id, color, level, experiece FROM User_Level ORDER BY color WHERE user_id = :user_id", nativeQuery = true)
    List<UserLevelResponseVO> getAllProgressing(@Param(value="user_id") int user_id);

    @Query(value="SELECT * FROM User_Level WHERE user_id = :user_id, color = :color", nativeQuery = true)
    UserLevelResponseVO getProgressingByColor(@Param(value="user_id") int user_id, @Param(value="color") int color);

    @Query(value="UPDATE User_Level SET level = :level, experience = :experience WHERE user_id = :user_id, color = :color", nativeQuery = true)
    UserLevelResponseVO updateUserLevel(@Param(value="level") int level, @Param(value="experience") int experience, @Param(value="user_id") int user_id, @Param(value="color") int Color);
}


/*


int _userID

ArrayList<UserLevelResponseVO> progressList= getProgressing(_userID)

int total = 0
for(int i = 0; i < progressList.length; i++){
    total += progressList[i].getTotalExperience()
}

ArrayList<ProgressingVO> result = new ArrayList<progressingVO>
for(int i = 0; i < progressList.length; i++){
    ArrayList.add(new ProgressingVO(progressList[i].getColor(), progressList[i].getTotalExperience()))
}

---------------------------------------------------------------------------------------------------------

게시글 작성했을 때

int _userID, _color
int _add :게시글을 작성하였으면 +1, 게시글을 삭제했으면 -1

UserLevelResponseDto ul = getProgressingByColor(_userID, _color);
if (_add == 1) {
    ul.level += (++ul.experience) / 100;
    ul.experience %= 100;
} else if (_add == -1) {
    if ((--ul.experience) == -1) {
        if (ul.level != 1) {
            --ul.level;
            ul.experience = 99;
        } else {
            ul.experience = 0;
        }
    }
}


updateUserLevel(ul.level, ul.experience, ul.user_id, ul.color)

게시글 삭제했을 때

int _userId, _color


 */